package com.zijie.eduservice.service;

import com.zijie.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zijie.eduservice.entity.chapter.ChapterVo;
import com.zijie.eduservice.entity.vo.CourseInfoVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-10-17
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterList(String courseId);

    boolean deleteChapter(String chapterId);

    void removeByCourseId(String courseId);
}
