package com.zijie.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.zijie.commonutils.R;
import com.zijie.servicebase.exception.MyException;
import com.zijie.vod.service.VodService;
import com.zijie.vod.utils.VodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author DZJ
 * @create 2021-10-21 20:16
 * @Description
 */
@RestController
@CrossOrigin
@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @PostMapping("/uploadVideo")
    public R uploadVideo(MultipartFile file) {
        //返回视频id
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId",videoId);
    }


    //根据videoSourceId删除阿里云视频
    @DeleteMapping("/removeAliYunVideo/{id}")
    public R removeAliYunVideo(@PathVariable String id) {
        boolean flag = vodService.removeAliYunVideo(id);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }

    }

    //删除多个视频
    @DeleteMapping("/removeVideos")
    public R removeVideos(@RequestParam("list") List<String > list) {
        boolean flag = vodService.removeVideos(list);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    //根据视频id获取视频凭证
    @GetMapping("/getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            DefaultAcsClient client = VodUtils.initVodClient(VodUtils.ACCESS_KEY_ID, VodUtils.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth", playAuth);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new MyException();
        }
    }

}
