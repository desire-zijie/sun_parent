package com.zijie.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zijie.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zijie.eduservice.entity.vo.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-10-11
 */
public interface EduTeacherService extends IService<EduTeacher> {

    //分页查询讲师
    Map<String, Object> getPageTeacherList(long page, long limit);

    Page<EduTeacher> pageList(long current, long limit);

    Page<EduTeacher> pageTeacherCondition(long current, long limit, TeacherQuery teacherQuery);
}
