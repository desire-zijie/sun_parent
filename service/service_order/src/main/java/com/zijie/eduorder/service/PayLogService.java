package com.zijie.eduorder.service;

import com.zijie.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
public interface PayLogService extends IService<PayLog> {

    //生成微信二维码
    Map createNative(String orderNo);

    //调用查询接口,查询订单支付状态
    Map<String, String> queryPayStatus(String orderNo);

    //更改订单状态,并且添加记录
    void updateOrderStatus(Map<String, String> map);
}
