package com.zijie.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zijie.commonutils.R;
import com.zijie.eduservice.entity.EduTeacher;
import com.zijie.eduservice.entity.vo.TeacherQuery;
import com.zijie.eduservice.service.EduTeacherService;
import com.zijie.servicebase.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-11
 */
@RestController
@RequestMapping("/eduservice/edu-teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    //查询所有讲师
    @GetMapping("/findAll")
    public R findAll() {
        List<EduTeacher> list = eduTeacherService.list(null);
        R r = R.ok().data("items", list);
        return r;
    }

    //通过id删除讲师
    @DeleteMapping("{id}")
    public R deleteById(@PathVariable String id) {
        boolean remove = eduTeacherService.removeById(id);
        return remove ? R.ok() : R.error();
    }

    //分页查询讲师
    @GetMapping("/pageListTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current, @PathVariable long limit) {
        Page<EduTeacher> page = eduTeacherService.pageList(current, limit);
        //返回总记录数和当前页数据
        return R.ok().data("total", page.getTotal()).data("teachers", page.getRecords());
    }

    //多条件分页查询讲师
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> page = eduTeacherService.pageTeacherCondition(current, limit, teacherQuery);
        return R.ok().data("total", page.getTotal()).data("teachers", page.getRecords());

    }

    //添加讲师
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        return save?R.ok():R.error();
    }

    //根据id查询讲师
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }

    //根据id修改讲师
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean update = eduTeacherService.updateById(eduTeacher);
        return update?R.ok():R.error();
    }
}

