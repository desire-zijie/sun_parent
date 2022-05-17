package com.zijie.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zijie.eduservice.entity.EduTeacher;
import com.zijie.eduservice.entity.vo.TeacherQuery;
import com.zijie.eduservice.mapper.EduTeacherMapper;
import com.zijie.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-10-11
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    //分页查询讲师
    @Override
    public Map<String, Object> getPageTeacherList(long page, long limit) {
        Page<EduTeacher> pageParam = new Page<>(page,limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //降序排序
        wrapper.orderByDesc("id");
        //分页查询
        baseMapper.selectPage(pageParam, wrapper);
        Map<String, Object> map = new HashMap<>();
        List<EduTeacher> records = pageParam.getRecords();//讲师记录
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();//上一页
        boolean hasPrevious = pageParam.hasPrevious();//下一页
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    //分页查询讲师
    @Override
    public Page<EduTeacher> pageList(long current, long limit) {
        //current当前页，limit每页记录数
        Page<EduTeacher> page = new Page<>(current, limit);
        this.page(page, null);
        return page;
    }

    //多条件分页查询讲师
    @Override
    public Page<EduTeacher> pageTeacherCondition(long current, long limit, TeacherQuery teacherQuery) {
        Page<EduTeacher> page = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //获取用户输入的插叙条件
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        //排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        this.page(page, wrapper);
        return page;
    }
}
