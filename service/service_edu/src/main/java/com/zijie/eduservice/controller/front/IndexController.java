package com.zijie.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zijie.commonutils.R;
import com.zijie.eduservice.entity.EduCourse;
import com.zijie.eduservice.entity.EduTeacher;
import com.zijie.eduservice.service.EduCourseService;
import com.zijie.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author DZJ
 * @create 2021-10-26 15:57
 * @Description
 */

@RestController
@CrossOrigin
@RequestMapping("/eduservice/index")
public class IndexController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;

    //查询前8个课程和前4个讲师
    @GetMapping("/index")
    public R index() {
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("id").last("limit 8");
        List<EduCourse> courseList = eduCourseService.list(courseQueryWrapper);

        QueryWrapper<EduTeacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("id").last("limit 4");
        List<EduTeacher> teacherList = eduTeacherService.list(teacherQueryWrapper);
        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}
