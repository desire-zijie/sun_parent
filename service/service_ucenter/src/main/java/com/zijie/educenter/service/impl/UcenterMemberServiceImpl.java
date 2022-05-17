package com.zijie.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijie.commonutils.JWTUtils;
import com.zijie.commonutils.MD5;
import com.zijie.educenter.entity.UcenterMember;
import com.zijie.educenter.entity.vo.UserVo;
import com.zijie.educenter.mapper.UcenterMemberMapper;
import com.zijie.educenter.service.UcenterMemberService;
import com.zijie.servicebase.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-10-28
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    //登录
    @Override
    public String login(UcenterMember ucenterMember) {
        //判断手机号和密码是否为空
        if (StringUtils.isEmpty(ucenterMember.getMobile()) || StringUtils.isEmpty(ucenterMember.getPassword())) {
            throw new MyException(20001, "手机号或密码为空");
        }
        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", ucenterMember.getMobile());
        UcenterMember member = baseMapper.selectOne(wrapper);
        if (member == null) {
            throw new MyException(20001, "找不到该用户");
        }
        //判断密码
        //把输入的密码进行加密，再和数据库密码进行比较
        //MD5加密，只能加密不可解密
        if (!MD5.encrypt(ucenterMember.getPassword()).equals(member.getPassword())) {
            throw new MyException(20001, "密码错误");
        }
        //判断用户是否禁用
        if (member.getIsDisabled()) {
            throw new MyException(20001, "登录失败");
        }
        //登录成功
        //生成token字符串，使用jwt工具类
        String token = JWTUtils.getJwtToken(member.getId(), member.getNickname());
        return token;
    }

    //注册
    @Override
    public void register(UserVo userVo) {
        String code = userVo.getCode();
        String mobile = userVo.getMobile();
        String password = userVo.getPassword();
        String nickname = userVo.getNickname();
        //判断是否为空
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
        || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(code)) {
            throw new MyException(20001, "注册失败");
        }
        String redisCode = redisTemplate.opsForValue().get(mobile);
        //判断验证码
        if (!code.equals(redisCode)) {
            throw new MyException(20001, "注册失败");
        }
        //判断手机号是否已经存在
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new MyException(20001, "注册失败");
        }
        //添加用户
        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        baseMapper.insert(member);

    }

    //根据openid查询用户
    @Override
    public UcenterMember getByOpenid(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    //获取某一天注册人数
    @Override
    public Integer getRegisterCount(String day) {
        return baseMapper.getRegisterCount(day);
    }
}
