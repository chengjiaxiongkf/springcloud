package com.user.controller;

import com.common.contants.CommonContants;
import com.common.util.RedisUtils;
import com.common.pojo.user.UserInfo;
import com.user.service.UserInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("userInfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("ribbonRestTemplate")
    private RestTemplate restTemplate;
    @Autowired
    private RedisUtils redisUtils;
    @Value("${server.port}")
    private String port;

    private final Logger log = Logger.getLogger(UserInfoController.class);
    /**
     * 获取用户信息
     * @return
     */
    @RequestMapping(value="/getUserInfo",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserInfo getUserInfo(UserInfo userInfo){
        log.info("user:"+port+" to getUserInfo(),param:"+userInfo);
        userInfo = userInfoService.getUserInfo(userInfo);
        log.info("user:"+port+" to getUserInfo(),result:"+userInfo);
        return userInfo;
    }

    /**
     * 负载均衡测试
     * @return
     */
    @RequestMapping(value="/getUserPort",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map getUserPort(){
        Map resultMap = new HashMap<>();
        resultMap.put("redis",redisUtils.get("SL_COMMON:REDISTEST:TESTA"));
        resultMap.put("servicenamer",CommonContants.serviceName);
        resultMap.put("port",port);
        log.info("redis:"+redisUtils.get("SL_COMMON:REDISTEST:TESTA"));
        log.info("servicenamer:"+CommonContants.serviceName);
        log.info("port:"+port);
        return resultMap;
    }
}
