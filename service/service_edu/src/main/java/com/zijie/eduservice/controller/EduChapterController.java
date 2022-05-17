package com.zijie.eduservice.controller;


import com.zijie.commonutils.R;
import com.zijie.eduservice.entity.EduChapter;
import com.zijie.eduservice.entity.chapter.ChapterVo;
import com.zijie.eduservice.entity.vo.CourseInfoVo;
import com.zijie.eduservice.service.EduChapterService;
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
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    //获取章节小节列表
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> chapterVoList = eduChapterService.getChapterList(courseId);
        return R.ok().data("chapterVideo",chapterVoList);
    }

    //添加章节
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        boolean save = eduChapterService.save(eduChapter);
        if (save) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    //根据id查询章节
    @GetMapping("/getChapter/{chapterId}")
    public R getChapter(@PathVariable String chapterId){
        EduChapter chapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter",chapter);
    }

    //修改章节
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        boolean update = eduChapterService.updateById(eduChapter);
        if (update) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    //通过id删除章节
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean flag = eduChapterService.deleteChapter(chapterId);
        return R.ok();
    }

}

