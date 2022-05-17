package com.zijie.eduservice.client;

import com.zijie.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author DZJ
 * @create 2021-10-25 20:11
 * @Description
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R removeAliYunVideo(String id) {
        return R.error().message("删除视频失败了，熔断器。。。");
    }

    @Override
    public R removeVideos(List<String> list) {
        return R.error().message("删除多个视频失败了，熔断器。。。");
    }
}
