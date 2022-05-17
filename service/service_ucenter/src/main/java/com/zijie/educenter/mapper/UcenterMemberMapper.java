package com.zijie.educenter.mapper;

import com.zijie.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-10-28
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    //获取某一天注册人数
    Integer getRegisterCount(String day);
}
