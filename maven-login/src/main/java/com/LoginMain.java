package com;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.jms.annotation.EnableJms;

/**
 * @Author chengjiaxiong
 * @Date 2020/7/30 0:44
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableJms
public class LoginMain {
    public static void main(String[] args) {
        new SpringApplicationBuilder(LoginMain.class).web(true).run(args);
    }
}
