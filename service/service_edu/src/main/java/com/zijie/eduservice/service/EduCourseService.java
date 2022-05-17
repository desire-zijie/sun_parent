package com.zijie.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zijie.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zijie.eduservice.entity.frontvo.CourseFrontVo;
import com.zijie.eduservice.entity.frontvo.CourseWebVo;
import com.zijie.eduservice.entity.vo.CourseInfoVo;
import com.zijie.eduservice.entity.vo.PublishCourseVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-10-17
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourse(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseVoInfo(String courseId);

    void updateCourse(CourseInfoVo courseInfoVo);

    PublishCourseVo getPublishCourse(String courseId);

    void updatePublishCourse(String courseId);

    //分页查询课程（前台）
    Map<String, Object> getFrontCourseList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo);

    //根据课程id查询课程详情信息（前台）
    CourseWebVo getFrontCourseInfo(String courseId);

    boolean deleteCourse(String courseId);
}
