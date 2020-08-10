package com.login.service.Impl;

import com.common.contants.login.LoginContants;
import com.login.service.LoginService;
import com.redis.contants.RedisContants;
import com.redis.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author chengjiaxiong
 * @Date 2020/8/10 7:52
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 校验登陆
     * @param loginUser
     * @param loginPassword
     * @return
     */
    @Override
    public boolean validLogin(String loginUser, String loginPassword) {
        String key = RedisContants.REDIS_LOGIN_USER+loginUser;
        Object object = redisUtils.get(key);
        if(null==object){//账号不存在
            return false;
        }
        Map map = new HashMap();
        map.put("loginPassword",loginPassword);
        map.put("key", LoginContants.MD5_PASSWORD_KEY);
        String sign = DigestUtils.md5DigestAsHex(map.toString().getBytes());
        if(!sign.equals(object.toString())){//密码不一致
            return false;
        }
        return true;
    }

    @Override
    public boolean register(String loginUser, String loginPassword) {
        String key = RedisContants.REDIS_LOGIN_USER+loginUser;
        Object object = redisUtils.get(key);
        if(null!=object){//已存在
            return false;
        }
        //新增账号信息
        Map map = new HashMap();
        map.put("loginPassword",loginPassword);
        map.put("key", LoginContants.MD5_PASSWORD_KEY);
        String sign = DigestUtils.md5DigestAsHex(map.toString().getBytes());
        redisUtils.set(key,sign);
        return true;
    }
}
