package com.zijie.msmservice.service;

/**
 * @author DZJ
 * @create 2022-05-10 19:07
 * @Description
 */

import org.springframework.stereotype.Service;

import java.util.Map;

public interface MsmService {
    public boolean send(Map<String, Object> param, String phone);
}
