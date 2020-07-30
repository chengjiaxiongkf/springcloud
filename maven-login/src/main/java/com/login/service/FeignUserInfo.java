package com.login.service;

import com.common.pojo.user.UserInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Author chengjiaxiong
 * @Date 2020/7/30 2:27
 */
@FeignClient("USER")
public interface FeignUserInfo {
    @RequestMapping("/userInfo/getUserInfo")
    UserInfo getUserInfo(UserInfo userInfo);

    @RequestMapping("/userInfo/getUserPort")
    Map getUserPort();
}
