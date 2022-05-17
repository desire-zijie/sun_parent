package com.zijie.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author DZJ
 * @create 2021-10-15 15:41
 * @Description
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@FeignClient
@EnableDiscoveryClient
@ComponentScan("com.zijie")
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }

}
