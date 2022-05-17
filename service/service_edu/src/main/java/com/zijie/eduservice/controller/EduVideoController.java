package com.zijie.eduservice.controller;


import com.zijie.commonutils.R;
import com.zijie.eduservice.client.VodClient;
import com.zijie.eduservice.entity.EduVideo;
import com.zijie.eduservice.service.EduVideoService;
import com.zijie.servicebase.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-17
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    @DeleteMapping("/deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable String videoId){
        //根据小节获取视频id
        EduVideo eduVideo = eduVideoService.getById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            //根据id，原程调用实现视频删除
            R r = vodClient.removeAliYunVideo(videoSourceId);
            if (!r.getSuccess()) {
                throw new MyException(20001, "删除视频出错了");
            }
        }
        eduVideoService.removeById(videoId);
        return R.ok();
    }
}

