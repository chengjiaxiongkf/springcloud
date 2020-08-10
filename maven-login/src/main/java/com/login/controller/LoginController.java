package com.login.controller;

import com.common.contants.CommonContants;
import com.common.contants.login.LoginContants;
import com.common.pojo.user.UserInfoPojo;
import com.common.vo.ResultVO;
import com.login.service.LoginService;
import com.login.service.feign.FeignUserInfo;
import com.netflix.discovery.converters.Auto;
import com.redis.contants.RedisContants;
import com.redis.util.RedisUtils;
import com.redis.util.RedissonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.util.HashMap;
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
    private RedisUtils redisUtils;
    @Autowired
    private LoginService loginService;

    /**
     * 注册
     * @param req
     * @param rep
     * @return
     */
    @RequestMapping(value = "/register",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultVO register(HttpServletRequest req,HttpServletResponse rep){
        ResultVO resultVO = new ResultVO();
        resultVO.setResultCode(CommonContants.SUCCESS);

        String loginUser = req.getParameter("loginUser");
        String loginPassword = req.getParameter("loginPassword");
        if(StringUtils.isEmpty(loginUser) || StringUtils.isEmpty(loginPassword)){
            resultVO.setResultCode(CommonContants.FAIL);
            resultVO.setResultMsg("账号/密码不能为空");
            return resultVO;
        }
        if(!loginService.register(loginUser,loginPassword)){
            resultVO.setResultCode(CommonContants.FAIL);
            resultVO.setResultMsg("账号已存在");
            return resultVO;
        }
        return resultVO;
    }

    /**
     * 登陆
     * @param req
     * @param rep
     * @return
     */
    @RequestMapping(value = "/checkLogin",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultVO login(HttpServletRequest req,HttpServletResponse rep){
        ResultVO resultVO = new ResultVO();
        resultVO.setResultCode(CommonContants.SUCCESS);

        String loginUser = req.getParameter("loginUser");
        String loginPassword = req.getParameter("loginPassword");
        if(loginUser==null || loginPassword==null){
            resultVO.setResultCode(CommonContants.FAIL);
            resultVO.setResultMsg("账号/密码不能为空");
            return resultVO;
        }
        if (!loginService.validLogin(loginUser,loginPassword)) {//校验账号密码是否正确
            resultVO.setResultCode(CommonContants.FAIL);
            resultVO.setResultMsg("账号/密码不正确");
            return resultVO;
        }
        Map<String,Object> userMap = new HashMap<>();
        userMap.put("loginUser",loginUser);
        userMap.put("loginPassword",loginPassword);
        userMap.put("key", LoginContants.MD5_LOGIN_KEY);
        String sign = DigestUtils.md5DigestAsHex(userMap.toString().getBytes());

        redisUtils.set(RedisContants.REDIS_LOGIN_TOKEN+sign,"1",LoginContants.LOGIN_VALID_TIME);//登陆缓存2分钟
        //新建Cookie
        Cookie cookie = new Cookie(LoginContants.COOKIE_SSOCOOKIE,sign);
        //设置Cookie路径
        cookie.setPath("/");//域名根目录
        rep.addCookie(cookie);
        return resultVO;
    }

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
