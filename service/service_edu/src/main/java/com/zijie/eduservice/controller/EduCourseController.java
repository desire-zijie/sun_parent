package com.zijie.eduservice.controller;


import com.zijie.commonutils.R;
import com.zijie.eduservice.entity.EduCourse;
import com.zijie.eduservice.entity.EduVideo;
import com.zijie.eduservice.entity.vo.CourseInfoVo;
import com.zijie.eduservice.entity.vo.PublishCourseVo;
import com.zijie.eduservice.service.EduChapterService;
import com.zijie.eduservice.service.EduCourseDescriptionService;
import com.zijie.eduservice.service.EduCourseService;
import com.zijie.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-17
 */
@RestController
@RequestMapping("/eduservice/edu-course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;



    //获取课程列表
    @GetMapping("/getCourseList")
    public R getCourseList() {
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("list", list);
    }


    //添加课程
    @PostMapping("/addCourse")
    public R addCourse(@RequestBody CourseInfoVo courseInfoVo) {
        String id = eduCourseService.addCourse(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    //通过课程id查询
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = eduCourseService.getCourseVoInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    //修改课程信息
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourse(courseInfoVo);
        return R.ok();
    }

    //课程最终发布，获取最终发布课程信息
    @GetMapping("/getPublishCourse/{courseId}")
    public R getPublishCourse(@PathVariable String courseId){
        PublishCourseVo publishCourseVo = eduCourseService.getPublishCourse(courseId);
        return R.ok().data("publishCourseVo",publishCourseVo);
    }

    //课程最终发布，修改最终课程信息status
    @PutMapping("/updatePublishCourse/{courseId}")
    public R updatePublishCourse(@PathVariable String courseId){
        eduCourseService.updatePublishCourse(courseId);
        return R.ok();
    }

    //删除课程
    @DeleteMapping("/{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        boolean remove =eduCourseService.deleteCourse(courseId);
        if(remove){
            return R.ok();
        }else {
            return R.error();
        }

    }
}

