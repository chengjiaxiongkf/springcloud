package com.login.controller;

import com.common.contants.CommonContants;
import com.common.pojo.user.UserInfoPojo;
import com.common.vo.ResultVO;
import com.login.service.feign.FeignUserInfo;
import com.redis.util.RedissonUtils;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class LoginController {
    @Autowired
    @Qualifier("ribbonRestTemplate")
    private RestTemplate restTemplate;
    @Autowired
    private FeignUserInfo feiUserInfo;
    @Autowired
    private RedissonUtils redisUtils;

    /**
     * 以ribbon方式调用user请求
     * @param req
     * @param rep
     * @return
     */
    @RequestMapping(value = "/getUserInfoByRibbon",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserInfoPojo getUserInfoByRibbon(HttpServletRequest req, HttpServletResponse rep){
        log.info("以ribbon方式访问user的用户信息");
        return restTemplate.getForObject(CommonContants.LOCATION_HTTP+"userservice/userInfo/getUserInfo", UserInfoPojo.class);
    }

    /**
     * 以feign方式调用user请求
     * @param req
     * @param rep
     * @return
     */
    @RequestMapping(value = "/getUserInfoByFeign",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserInfoPojo getUserInfoByFeign(HttpServletRequest req, HttpServletResponse rep){
        log.info("以feign方式访问user的用户信息");
        return feiUserInfo.getUserInfo(new UserInfoPojo());
    }

    /**
     * 以feign方式获取user的port
     * @param req
     * @param rep
     * @return
     */
    @RequestMapping(value = "/getUserPort",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map getUserPort(HttpServletRequest req, HttpServletResponse rep){
        log.info("以feign方式访问user的端口信息");
        return feiUserInfo.getUserPort();
    }

    @RequestMapping(value = "/getRedisValueByKey",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultVO getRedisValueByKey(HttpServletRequest request){
        ResultVO requestVO = new ResultVO();
        requestVO.setResultCode(CommonContants.SUCCESS);
        String key = request.getParameter("key");
        requestVO.setResultMsg("");
        return requestVO;
    }
}
