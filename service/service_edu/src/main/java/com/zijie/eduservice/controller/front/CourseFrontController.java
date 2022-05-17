package com.zijie.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.zijie.commonutils.CourseOrderVo;
import com.zijie.commonutils.JWTUtils;
import com.zijie.commonutils.R;
import com.zijie.eduservice.client.OrderClient;
import com.zijie.eduservice.entity.EduCourse;
import com.zijie.eduservice.entity.chapter.ChapterVo;
import com.zijie.eduservice.entity.frontvo.CourseFrontVo;
import com.zijie.eduservice.entity.frontvo.CourseWebVo;
import com.zijie.eduservice.entity.vo.CourseInfoVo;
import com.zijie.eduservice.service.EduChapterService;
import com.zijie.eduservice.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author DZJ
 * @create 2021-10-31 20:50
 * @Description
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/courseFront")
public class CourseFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private OrderClient orderClient;

    //分页查询课程（前台）
    @PostMapping("getCourseList/{page}/{limit}")
    public R getCourseList(@PathVariable long page,
                           @PathVariable long limit,
                           @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> coursePage = new Page<>(page,limit);
        Map<String, Object> map = eduCourseService.getFrontCourseList(coursePage, courseFrontVo);
        return R.ok().data(map);
    }

    //根据课程id查询课程详情信息（前台）
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        //查询课程基本信息，包括讲师，描述
        CourseWebVo courseWebVo = eduCourseService.getFrontCourseInfo(courseId);
        //查询章节小节
        List<ChapterVo> chapterList = eduChapterService.getChapterList(courseId);
        //根据课程id和用户id和订单状态判断用户是否已购买此课程
        boolean isbuy = orderClient.isBuyCourse(courseId, JWTUtils.getMemberIdByJwtToken(request));
        return R.ok().data("courseWebVo",courseWebVo).data("chapterList",chapterList).data("isbuy",isbuy);
    }

    //根据课程id查询课程详情信息（用于订单需要的数据）
    @GetMapping("/getOrderCourseInfo/{courseId}")
    public CourseOrderVo getOrderCourseInfo(@PathVariable String courseId){
        //查询课程基本信息，包括讲师，描述
        CourseWebVo courseWebVo = eduCourseService.getFrontCourseInfo(courseId);
        CourseOrderVo courseOrderVo = new CourseOrderVo();
        BeanUtils.copyProperties(courseWebVo,courseOrderVo);
        return courseOrderVo;
    }
}
