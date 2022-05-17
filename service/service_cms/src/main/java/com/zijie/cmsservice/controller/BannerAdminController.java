package com.zijie.cmsservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zijie.cmsservice.entity.CrmBanner;
import com.zijie.cmsservice.service.CrmBannerService;
import com.zijie.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-26
 */
@RestController
@RequestMapping("/cmsservice/banner_admin")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;

    //查询幻灯片分页查询
    @GetMapping("getBannerPage/{page}/{limit}")
    public R index(@PathVariable long page, @PathVariable long limit) {
        //根据id进行降序排序，并且显示两条记录
        Page<CrmBanner> crmBannerPage = new Page<>(page, limit);
        crmBannerService.page(crmBannerPage, null);
        return R.ok().data("banners", crmBannerPage.getRecords()).data("total", crmBannerPage.getTotal());
    }

    //添加幻灯片
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        crmBannerService.save(crmBanner);
        return R.ok();
    }

    //修改
    @PutMapping("updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner) {
        crmBannerService.updateById(crmBanner);
        return R.ok();
    }

    //根据id查询
    @GetMapping("getBannerById/{id}")
    public R getBannerById(@PathVariable String id) {
        crmBannerService.getById(id);
        return R.ok();
    }


    //删除
    @DeleteMapping("deleteBanner/{id}")
    public R deleteBanner(@PathVariable String id) {
        crmBannerService.removeById(id);
        return R.ok();
    }



}

