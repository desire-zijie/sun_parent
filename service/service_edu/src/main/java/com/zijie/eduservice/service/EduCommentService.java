package com.zijie.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zijie.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
public interface EduCommentService extends IService<EduComment> {

    //分页查询课程评论
    Map<String, Object> getPageComment(Page<EduComment> commentPage,String courseId);
}
