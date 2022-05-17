package com.zijie.eduorder.service;

import com.zijie.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
public interface OrderService extends IService<Order> {

    //生成订单的方法
    String createOrder(String courseId, String userId);
}
