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

    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    //根据视频id删除视频
    @DeleteMapping("/deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable String videoId){
        if (eduVideoService.deleteVideo(videoId)) {
            return R.ok();
        }else {
            return R.error().message("删除视频失败");
        }
    }
}

