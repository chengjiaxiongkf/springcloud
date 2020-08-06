package com.login.service.feign;

import com.common.pojo.user.UserInfoPojo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Author chengjiaxiong
 * @Date 2020/7/30 2:27
 */
@FeignClient("userservice")//注册到eureka,zl前缀说明提供给zuul的名称的application名
public interface FeignUserInfo {
    @RequestMapping("/userInfo/getUserInfo")
    UserInfoPojo getUserInfo(UserInfoPojo userInfo);

    @RequestMapping("/userInfo/getUserPort")
    Map getUserPort();
}
