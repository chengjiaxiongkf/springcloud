package com.login.service;

/**
 * @Author chengjiaxiong
 * @Date 2020/8/10 7:52
 */
public interface LoginService {
    boolean validLogin(String loginUser,String loginPassword);//校验登陆

    boolean register(String loginUser,String loginPassword);//注册
}
