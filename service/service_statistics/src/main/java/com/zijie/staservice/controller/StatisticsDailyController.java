package com.zijie.staservice.controller;


import com.zijie.commonutils.R;
import com.zijie.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-03
 */
@RestController
@CrossOrigin
@RequestMapping("/staservice/statistics-daily")
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService service;

    //统计某一天注册人数,生成统计数据
    @PostMapping("/registerCount/{day}")
    public R registerCount(@PathVariable String day) {
        service.registerCount(day);
        return R.ok();
    }

    //生成图表数据，传给前端日期json数组，数量json数组
    //type表示登录或注册数量，等等
    @GetMapping("/getEchartShow/{type}/{begin}/{end}")
    public R getEchartShow(@PathVariable String type,@PathVariable String begin,
                           @PathVariable String end) {
        Map map = service.getEchartShow(type, begin, end);
        return R.ok().data(map);
    }


}

