package com.zijie.staservice.service;

import com.zijie.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-03
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    //统计某一天注册人数,生成统计数据
    void registerCount(String day);

    //生成图表数据，传给前端日期json数组，数量json数组
    Map<String,Object> getEchartShow(String type, String begin, String end);
}
