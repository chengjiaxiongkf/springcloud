package com;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Author chengjiaxiong
 * @Date 2020/7/30 12:53
 */
@SpringBootApplication
@EnableZuulProxy
public class ZuulMain {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ZuulMain.class).web(true).run(args);
    }
}
