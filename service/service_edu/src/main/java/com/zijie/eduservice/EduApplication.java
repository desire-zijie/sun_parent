package com.zijie.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author DZJ
 * @create 2021-10-11 19:35
 * @Description
 */
@SpringBootApplication
@EnableDiscoveryClient //nacos 注册
@EnableFeignClients
@ComponentScan("com.zijie")
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
