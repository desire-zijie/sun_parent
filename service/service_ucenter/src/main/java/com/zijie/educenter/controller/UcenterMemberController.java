package com.zijie.educenter.controller;


import com.zijie.commonutils.JWTUtils;
import com.zijie.commonutils.R;
import com.zijie.commonutils.UcenterMemberVo;
import com.zijie.educenter.entity.UcenterMember;
import com.zijie.educenter.entity.vo.UserVo;
import com.zijie.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-28
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    //登录
    @PostMapping("/login")
    public R login(@RequestBody UcenterMember ucenterMember) {
        String token = ucenterMemberService.login(ucenterMember);
        return R.ok().data("token",token);
    }

    //注册
    @PostMapping("/register")
    public R register(@RequestBody UserVo userVo) {
        ucenterMemberService.register(userVo);
        return R.ok();
    }

    //根据token获取用户信息
    @GetMapping("/getUserInfo")
    public R getUserInfo(HttpServletRequest request) {
        String memberId = JWTUtils.getMemberIdByJwtToken(request);
        UcenterMember member = ucenterMemberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }

    //根据id获取用户信息
    @GetMapping("/getUserById/{id}")
    public com.zijie.commonutils.UcenterMemberVo getUserById(@PathVariable String id) {
        UcenterMember member = ucenterMemberService.getById(id);
        com.zijie.commonutils.UcenterMemberVo ucenterMemberVo = new UcenterMemberVo();
        BeanUtils.copyProperties(member,ucenterMemberVo);
        return ucenterMemberVo;
    }

    //获取某一天注册人数
    @GetMapping("/getRegister/{day}")
    public Integer getRegister(@PathVariable String day) {
        Integer count = ucenterMemberService.getRegisterCount(day);
        return count;
    }
}

