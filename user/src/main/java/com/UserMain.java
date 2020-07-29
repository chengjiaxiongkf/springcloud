
package com;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UserMain {
    public static void main(String[] args) {
        new SpringApplicationBuilder(UserMain.class).web(true).run(args);
    }
}