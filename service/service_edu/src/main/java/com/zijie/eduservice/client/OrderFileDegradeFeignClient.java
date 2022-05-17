package com.zijie.eduservice.client;

import org.springframework.stereotype.Component;

/**
 * @author DZJ
 * @create 2021-11-03 17:10
 * @Description
 */
@Component
public class OrderFileDegradeFeignClient implements OrderClient {
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }
}
