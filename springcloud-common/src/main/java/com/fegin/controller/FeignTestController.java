package com.fegin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignTestController {
    @Value("${server.port}")
    private String port;

    @RequestMapping("/findFeignMethodA")
    public String findFeignMethodA() {
        return "feignA:"+port;
    }

    @RequestMapping("/findFeignMethodB")
    public String findFeignMethodB(){
        return "feignB:"+port;
    }

    @RequestMapping("/findFeignMethodC")
    public String findFeignMethodC(){
        return "feignC:"+port;
    }
}
