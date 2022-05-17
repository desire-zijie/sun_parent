package com.zijie.cmsservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author DZJ
 * @create 2021-10-26 15:39
 * @Description
 */
@SpringBootApplication
@ComponentScan({"com.zijie"}) //指定扫描位置
@MapperScan("com.zijie.cmsservice.mapper")
@EnableDiscoveryClient //nacos 注册
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
