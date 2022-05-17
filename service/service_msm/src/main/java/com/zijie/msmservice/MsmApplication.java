package com.zijie.msmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author DZJ
 * @create 2022-05-10 19:50
 * @Description
 */
@SpringBootApplication
@EnableDiscoveryClient //nacos 注册
@EnableFeignClients
@ComponentScan("com.zijie")
public class MsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsmApplication.class, args);
    }
}
