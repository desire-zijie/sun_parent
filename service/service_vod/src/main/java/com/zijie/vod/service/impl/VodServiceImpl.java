package com.zijie.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.zijie.commonutils.R;
import com.zijie.servicebase.exception.MyException;
import com.zijie.vod.service.VodService;
import com.zijie.vod.utils.VodUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author DZJ
 * @create 2021-10-21 20:19
 * @Description
 */
@Service
public class VodServiceImpl implements VodService {

    //上传视频到阿里云
    @Override
    public String uploadVideo(MultipartFile file) {
        String accessKeyId= VodUtils.ACCESS_KEY_ID;
        String accessKeySecret = VodUtils.ACCESS_KEY_SECRET;
        //fileName：上传文件原始名称
        String fileName = file.getOriginalFilename();
        //title：上传之后显示名称
        String title = fileName.substring(0,fileName.lastIndexOf("."));
        InputStream is = null;
        try {
            is = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, is);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }
            return videoId;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean removeAliYunVideo(String id) {
        DefaultAcsClient client = VodUtils.initVodClient(VodUtils.ACCESS_KEY_ID, VodUtils.ACCESS_KEY_SECRET);
        DeleteVideoRequest request = new DeleteVideoRequest();
        try {
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(id);
            //实现删除
            client.getAcsResponse(request);
            return true;
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
            throw new MyException(20001, "删除失败");
        }
    }

    //删除多个视频
    @Override
    public boolean removeVideos(List<String> list) {
        DefaultAcsClient client = VodUtils.initVodClient(VodUtils.ACCESS_KEY_ID, VodUtils.ACCESS_KEY_SECRET);
        DeleteVideoRequest request = new DeleteVideoRequest();
        String videos = StringUtils.join(list.toArray(), ',');
        try {
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(videos);
            //实现删除
            client.getAcsResponse(request);
            return true;
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
            throw new MyException(20001, "删除失败");
        }
    }
}
