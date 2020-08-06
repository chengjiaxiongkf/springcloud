package com;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @Author chengjiaxiong
 * @Date 2020/8/6 10:48
 */
@SpringBootApplication
public class WebSocketMain {
    public static void main(String[] args) {
        new SpringApplicationBuilder(WebSocketMain.class).web(true).run(args);
    }
}
