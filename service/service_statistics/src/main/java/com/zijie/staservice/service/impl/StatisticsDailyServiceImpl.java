package com.zijie.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijie.staservice.client.UcenterClient;
import com.zijie.staservice.entity.StatisticsDaily;
import com.zijie.staservice.mapper.StatisticsDailyMapper;
import com.zijie.staservice.service.StatisticsDailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-11-03
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    //统计某一天注册人数,生成统计数据
    @Override
    public void registerCount(String day) {
        //添加记录之前删除表相同日期的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        Integer register = ucenterClient.getRegister(day);
        //把获取数据添加数据库，统计分析表里面
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setDateCalculated(day);//统计日期
        statisticsDaily.setRegisterNum(register);//注册人数

        statisticsDaily.setLoginNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100, 200));
        baseMapper.insert(statisticsDaily);
    }

    //生成图表数据，传给前端日期json数组，数量json数组
    @Override
    public Map<String, Object> getEchartShow(String type, String begin, String end) {
        //查询数据库获取数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.select("date_calculated", type);
        wrapper.orderByDesc("date_calculated");
        List<StatisticsDaily> list = baseMapper.selectList(wrapper);
        //封装数据返回
        List<Integer> numList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        for (StatisticsDaily daily : list) {
            dateList.add(daily.getDateCalculated());
            switch (type){
                case "login_num":
                    numList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numList.add(daily.getCourseNum());
                    break;
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("numList", numList);
        map.put("dateList", dateList);
        return map;
    }
}
