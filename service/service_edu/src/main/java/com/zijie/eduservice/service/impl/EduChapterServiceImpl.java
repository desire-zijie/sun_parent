package com.zijie.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zijie.eduservice.entity.EduChapter;
import com.zijie.eduservice.entity.EduVideo;
import com.zijie.eduservice.entity.chapter.ChapterVo;
import com.zijie.eduservice.entity.chapter.VideoVo;
import com.zijie.eduservice.entity.vo.CourseInfoVo;
import com.zijie.eduservice.mapper.EduChapterMapper;
import com.zijie.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijie.eduservice.service.EduVideoService;
import com.zijie.servicebase.exception.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-10-17
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    //获取章节小节列表
    @Override
    public List<ChapterVo> getChapterList(String courseId) {
        //查询章节列表
        QueryWrapper<EduChapter> eduChapterQueryWrapper = new QueryWrapper<>();
        eduChapterQueryWrapper.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(eduChapterQueryWrapper);
        //查询小节列表
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id", courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(eduVideoQueryWrapper);
        //最终返回的数据
        ArrayList<ChapterVo> finalChapters = new ArrayList<>();
        //封装章节
        for (EduChapter eduChapter : eduChapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            finalChapters.add(chapterVo);
            //封装小节进入章节
            ArrayList<VideoVo> videoVos = new ArrayList<>();
            for (EduVideo eduVideo : eduVideoList) {
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVos);
        }
        return finalChapters;
    }

    //根据课程id删除章节
    @Override
    public void removeByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        //查询章节中是否含有小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = eduVideoService.count(wrapper);
        if (count > 0) {//章节中包含小节，无法删除
            throw new MyException(20001, "删除失败");
        }
        int flag = baseMapper.deleteById(chapterId);
        return flag>0;
    }
}
