package com.zijie.eduservice.client;

import com.zijie.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author DZJ
 * @create 2021-10-24 21:38
 * @Description
 */
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {

    //定义调用方法的路径
    //根据videoSourceId删除阿里云视频
    //@PathVariable需要有参数名称，否则会出错
    @DeleteMapping("/eduvod/video/removeAliYunVideo/{id}")
    public R removeAliYunVideo(@PathVariable("id") String id) ;


    //删除多个视频
    @DeleteMapping("/eduvod/video/removeVideos")
    public R removeVideos(@RequestParam("list") List<String > list);
}
