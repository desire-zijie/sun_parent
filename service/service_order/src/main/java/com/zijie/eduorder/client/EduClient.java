package com.zijie.eduorder.client;

import com.zijie.commonutils.CourseOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author DZJ
 * @create 2021-11-02 17:14
 * @Description
 */
@FeignClient("service-edu")
@Component
public interface EduClient {

    //根据课程id查询课程详情信息（用于订单需要的数据）
    @GetMapping("/eduservice/courseFront/getOrderCourseInfo/{courseId}")
    public CourseOrderVo getOrderCourseInfo(@PathVariable String courseId);
}
