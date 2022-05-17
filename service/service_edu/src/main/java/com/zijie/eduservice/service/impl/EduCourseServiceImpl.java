package com.zijie.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zijie.eduservice.entity.EduCourse;
import com.zijie.eduservice.entity.EduCourseDescription;
import com.zijie.eduservice.entity.EduTeacher;
import com.zijie.eduservice.entity.frontvo.CourseFrontVo;
import com.zijie.eduservice.entity.frontvo.CourseWebVo;
import com.zijie.eduservice.entity.vo.CourseInfoVo;
import com.zijie.eduservice.entity.vo.PublishCourseVo;
import com.zijie.eduservice.mapper.EduCourseMapper;
import com.zijie.eduservice.service.EduChapterService;
import com.zijie.eduservice.service.EduCourseDescriptionService;
import com.zijie.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijie.eduservice.service.EduVideoService;
import com.zijie.servicebase.exception.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-10-17
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduCourseService eduCourseService;

    //添加课程方法
    @Override
    public String addCourse(CourseInfoVo courseInfoVo) {
        //添加课程
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            throw new MyException(20001, "添加课程失败");
        }
        //添加课程描述  1对1关系
        String id = eduCourse.getId();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(id);
        boolean save = eduCourseDescriptionService.save(eduCourseDescription);
        if (!save) {
            throw new MyException(20001, "添加课程失败信息");
        }
        return id;
    }

    ////通过课程id查询课程信息
    @Override
    public CourseInfoVo getCourseVoInfo(String courseId) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        //查询课程信息
        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        //查询课程简介信息
        EduCourseDescription description = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(description.getDescription());
        return courseInfoVo;
    }

    //课程最终发布，获取最终发布课程信息
    @Override
    public PublishCourseVo getPublishCourse(String courseId) {
        PublishCourseVo publishCourse = baseMapper.getPublishCourse(courseId);
        return publishCourse;
    }

    //修改课程信息
    @Override
    public void updateCourse(CourseInfoVo courseInfoVo) {
        //1 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0) {
            throw new MyException(20001,"修改课程信息失败");
        }
        //2 修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setDescription(courseInfoVo.getDescription());
        description.setId(courseInfoVo.getId());
        eduCourseDescriptionService.updateById(description);
    }

    //课程最终发布，修改最终课程信息status
    @Override
    public void updatePublishCourse(String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        baseMapper.updateById(eduCourse);
    }

    //分页查询课程（前台）
    @Override
    public Map<String, Object> getFrontCourseList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {//一级分类
            wrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) { //二级分类
            wrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(pageParam, wrapper);
        Map<String, Object> map = new HashMap<>();
        List<EduCourse> records = pageParam.getRecords();//课程记录
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

    //根据课程id查询课程详情信息（前台）
    @Override
    public CourseWebVo getFrontCourseInfo(String courseId) {
        return baseMapper.getFrontCourseInfo(courseId);
    }

    //删除课程
    @Override
    public boolean deleteCourse(String courseId) {
        //删除小节
        eduVideoService.removeByCourseId(courseId);
        //删除章节
        eduChapterService.removeByCourseId(courseId);
        //删除课程描述
        eduCourseDescriptionService.removeById(courseId);
        //删除课程
        boolean remove = eduCourseService.removeById(courseId);
        return remove;
    }
}
