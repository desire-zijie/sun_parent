package com.zijie.cmsservice.controller;

import com.zijie.cmsservice.entity.CrmBanner;
import com.zijie.cmsservice.service.CrmBannerService;
import com.zijie.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author DZJ
 * @create 2021-10-26 15:42
 * @Description
 */
@RestController
@RequestMapping("/cmsservice/banner_front")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService crmBannerService;

    //获取所有幻灯片

    @GetMapping("getBannerList")
    public R getBannerList() {
        List<CrmBanner> list = crmBannerService.selectBanner();
        return R.ok().data("list",list);
    }
}
