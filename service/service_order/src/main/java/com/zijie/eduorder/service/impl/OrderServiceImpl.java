package com.zijie.eduorder.service.impl;

import com.zijie.commonutils.CourseOrderVo;
import com.zijie.commonutils.UcenterMemberVo;
import com.zijie.eduorder.client.EduClient;
import com.zijie.eduorder.client.UcenterClient;
import com.zijie.eduorder.entity.Order;
import com.zijie.eduorder.mapper.OrderMapper;
import com.zijie.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijie.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private EduClient eduClient;

    //生成订单的方法
    @Override
    public String createOrder(String courseId, String userId) {
        //远程调用接口，查询课程信息
        CourseOrderVo courseDto = eduClient.getOrderCourseInfo(courseId);
        //远程调用接口，查询用户信息
        UcenterMemberVo ucenterMember = ucenterClient.getUserById(userId);
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseDto.getTitle());
        order.setCourseCover(courseDto.getCover());
        order.setTeacherName(courseDto.getTeacherName());
        order.setTotalFee(courseDto.getPrice());
        order.setMemberId(userId);
        order.setMobile(ucenterMember.getMobile());
        order.setNickname(ucenterMember.getNickname());
        order.setStatus(0);//设置支付状态，0 未支付 1 已支付
        order.setPayType(1);//设置支付类型 1 微信
        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
