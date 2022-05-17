package com.zijie.eduservice.client;

import com.zijie.commonutils.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author DZJ
 * @create 2021-11-02 11:14
 * @Description
 */
@FeignClient(name = "service-ucenter",fallback = UcenterFileDegradeFeignClient.class)
@Component
public interface UcenterClient {

    //根据id获取用户信息
    @GetMapping("/educenter/member/getUserById/{id}")
    public UcenterMemberVo getUserById(@PathVariable("id") String id);
}
