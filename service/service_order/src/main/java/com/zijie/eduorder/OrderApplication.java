package com.zijie.eduorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author DZJ
 * @create 2021-11-02 17:03
 * @Description
 */
@SpringBootApplication
@EnableDiscoveryClient //nacos 注册
@EnableFeignClients
@ComponentScan("com.zijie")
@MapperScan("com.zijie.eduorder.mapper")
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
