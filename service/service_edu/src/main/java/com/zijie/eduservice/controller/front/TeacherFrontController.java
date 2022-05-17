package com.zijie.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zijie.commonutils.R;
import com.zijie.eduservice.entity.EduCourse;
import com.zijie.eduservice.entity.EduTeacher;
import com.zijie.eduservice.service.EduCourseService;
import com.zijie.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author DZJ
 * @create 2021-10-31 11:03
 * @Description
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacherFront")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    //分页查询讲师
    @GetMapping("/getTeacherList/{page}/{limit}")
    public R getTeacherList(@PathVariable long page,
                            @PathVariable long limit) {
        Map<String, Object> map = teacherService.getPageTeacherList(page, limit);
        return R.ok().data(map);
    }

    //根据teacherid查询讲师
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        //查询讲师
        EduTeacher teacher = teacherService.getById(id);
        //查询讲师所讲的课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", id);
        List<EduCourse> courseList = courseService.list(wrapper);
        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }
}
