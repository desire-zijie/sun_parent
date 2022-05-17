package com.zijie.staservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author DZJ
 * @create 2021-11-03 20:56
 * @Description
 */
@FeignClient(name = "service-ucenter")
@Component
public interface UcenterClient {

    //获取某一天注册人数
    @GetMapping("/educenter/member/getRegister/{day}")
    public Integer getRegister(@PathVariable("day") String day);
}
