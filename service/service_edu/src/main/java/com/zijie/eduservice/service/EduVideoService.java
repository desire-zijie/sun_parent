package com.zijie.eduservice.service;

import com.zijie.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-10-17
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeByCourseId(String courseId);
}
