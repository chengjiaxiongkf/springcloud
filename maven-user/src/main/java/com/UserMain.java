
package com;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.jms.annotation.EnableJms;

/**
 * @Author chengjiaxiong
 * @Date 2020/7/30 0:44
 */
@SpringBootApplication //springboot项目启动
@EnableEurekaClient //这是一个eureka的客户端123456
@EnableJms //启用消息队列
public class UserMain {
    public static void main(String[] args) {
        new SpringApplicationBuilder(UserMain.class).web(true).run(args);
    }
}