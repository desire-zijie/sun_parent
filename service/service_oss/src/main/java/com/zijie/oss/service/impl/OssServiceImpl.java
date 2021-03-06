package com.zijie.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.zijie.oss.service.OssService;
import com.zijie.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author DZJ
 * @create 2021-10-15 15:51
 * @Description
 */
@Service
public class OssServiceImpl implements OssService {

    //上传头像
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

    // 创建OSS实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        InputStream inputStream = null;
        try {
            //获取上传文件输入流
            inputStream = file.getInputStream();
            //获取文件名称
            String filename = file.getOriginalFilename();

            //1、在文件名称加入随机唯一值
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //获取当前日期  2021/10/15
            //2、把文件按照日期进行分类
            String date = new DateTime().toString("yyyy/MM/dd");
            //3、拼接   2019/11/12/ewtqr313401.jpg
            filename = date + "/" + uuid + filename;

            //调用oss 方法实现上传
            //第一个参数  Bucket名称
            //第二个参数  上传到oss文件路径和文件名称   aa/bb/1.jpg
            //第三个参数  上传文件输入流
            ossClient.putObject(bucketName, filename, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传之后文件路径返回
            //需要把上传到阿里云oss路径手动拼接出来
            //  https://edu-guli-1010.oss-cn-beijing.aliyuncs.com/01.jpg
            String url = "https://"+bucketName+"."+endpoint+"/"+filename;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
