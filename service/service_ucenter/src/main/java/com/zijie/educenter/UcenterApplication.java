package com.zijie.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author DZJ
 * @create 2021-10-28 16:26
 * @Description
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.zijie")
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.zijie.educenter.mapper")
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class,args);
    }
}
