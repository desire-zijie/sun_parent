package com.zijie.msmservice.controller;

import com.zijie.commonutils.R;
import com.zijie.msmservice.service.MsmService;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author DZJ
 * @create 2021-10-28 14:55
 * @Description
 */
@RestController
@RequestMapping("/edumsm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("send/{phone}")
    public R sendSms(@PathVariable String phone) {
        //1、从redis中获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok().data("code", code);
        }
        code = String.valueOf((int)((Math.random()*9+1)*1000));

        Map<String, Object> param = new HashMap<>();
        param.put("code",code);
        //调用service的方法，发送
        boolean isSend = msmService.send(param,phone);
        if (isSend) {
            //阿里云发送成功，把发送成功的验证码放入redis缓存中
            //设置有效时间
            redisTemplate.opsForValue().set(phone,code,3, TimeUnit.MINUTES);
            return R.ok();
        }else
            return R.error().message("短信发送失败");
    }

}
