package com.zijie.eduservice.mapper;

import com.zijie.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zijie.eduservice.entity.frontvo.CourseWebVo;
import com.zijie.eduservice.entity.vo.PublishCourseVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-10-17
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    PublishCourseVo getPublishCourse(String courseId);

    //根据课程id查询课程详情信息（前台）
    CourseWebVo getFrontCourseInfo(String courseId);
}
