package com.login.controller;

import com.common.contants.CommonContants;
import com.common.pojo.user.UserInfo;
import com.login.service.FeignUserInfo;
import org.apache.catalina.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author chengjiaxiong
 * @Date 2020/7/30 1:00
 */
@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    @Qualifier("ribbonRestTemplate")
    private RestTemplate restTemplate;
    @Autowired
    private FeignUserInfo feiUserInfo;

    private Logger log = Logger.getLogger(LoginController.class);
    /**
     * 以ribbon方式调用user请求
     * @param req
     * @param rep
     * @return
     */
    @RequestMapping(value = "/getUserInfoByRibbon",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserInfo getUserInfoByRibbon(HttpServletRequest req, HttpServletResponse rep){
        return restTemplate.getForObject(CommonContants.LOCATION_HTTP+"user/userInfo/getUserInfo", UserInfo.class);
    }

    /**
     * 以feign方式调用user请求
     * @param req
     * @param rep
     * @return
     */
    @RequestMapping(value = "/getUserInfoByFeign",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserInfo getUserInfoByFeign(HttpServletRequest req, HttpServletResponse rep){
        return feiUserInfo.getUserInfo(new UserInfo());
    }

    /**
     * 以feign方式获取user的port
     * @param req
     * @param rep
     * @return
     */
    @RequestMapping(value = "/getUserPort",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map getUserPort(HttpServletRequest req, HttpServletResponse rep){
        return feiUserInfo.getUserPort();
    }
}
