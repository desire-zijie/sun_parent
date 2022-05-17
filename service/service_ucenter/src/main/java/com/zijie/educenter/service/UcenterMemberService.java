package com.zijie.educenter.service;

import com.zijie.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zijie.educenter.entity.vo.UserVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-10-28
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember ucenterMember);

    //注册
    void register(UserVo userVo);

    //根据openid查询用户
    UcenterMember getByOpenid(String openid);

    //获取某一天注册人数
    Integer getRegisterCount(String day);
}
