package com.zijie.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zijie.commonutils.R;
import com.zijie.eduorder.entity.PayLog;
import com.zijie.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
@RestController
@RequestMapping("/eduorder/pay-log")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    //生成微信二维码
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {
        Map map = payLogService.createNative(orderNo);
        System.out.println(map);
        return R.ok().data(map);
    }

    //查询订单支付状态 支付成功则修改支付状态以及向支付订单表添加一条记录
    @GetMapping("/getOrderStatus/{orderNo}")
    public R getOrderStatus(@PathVariable String orderNo) {
        //调用查询接口,查询订单支付状态
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        System.out.println(map);
        if (map == null) {//出错
            return R.error().message("支付出错");
        }
        if (map.get("trade_state").equals("SUCCESS")) {//如果成功
            //更改订单状态,并且添加记录
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }



}

