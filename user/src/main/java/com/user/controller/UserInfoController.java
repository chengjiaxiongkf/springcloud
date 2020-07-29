package com.user.controller;

import com.common.contants.CommonContants;
import com.common.util.RedisUtils;
import com.common.vo.RequestVO;
import com.common.pojo.user.UserInfo;
import com.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("userInfoController")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("rabbitRestTemplate")
    private RestTemplate restTemplate;
    @Autowired
    private RedisUtils redisUtils;
    /**
     * 获取用户信息
     * @param req
     * @param rep
     * @return
     */
    @RequestMapping(value="/getUserInfo",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RequestVO getUserInfo(HttpServletRequest req, HttpServletResponse rep){
        RequestVO requestResult = new RequestVO();
        requestResult.setResultCode(CommonContants.SUCCESS);
        requestResult.setData(userInfoService.getUserInfo(new UserInfo()));
        requestResult.setResultMsg(redisUtils.get("SL_COMMON:REDISTEST:TESTA")+"");
        return requestResult;
    }
    /**
     * 直接通过应用实例名访问其他应用的controller请求
     * @return
     */
    @GetMapping("/getServiceByInstanceId")
    public String getServiceC() {
        return restTemplate.getForObject("http://springcloud-consume/consume/getServiceByInstanceId", String.class);//直接通过应用名字访问服务
    }
}
