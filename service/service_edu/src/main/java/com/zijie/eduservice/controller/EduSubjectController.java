package com.zijie.eduservice.controller;


import com.zijie.commonutils.R;
import com.zijie.eduservice.entity.subject.OneSubject;
import com.zijie.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-16
 */
@RestController
@RequestMapping("/eduservice/edu-subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    //通过上传excel文件进行添加
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file) {
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }

    //课程分类列表
    @GetMapping("/getAllList")
    public R getAllList() {
        List<OneSubject> oneSubjectList = eduSubjectService.getSubjectList();
        return R.ok().data("list",oneSubjectList);
    }

}

