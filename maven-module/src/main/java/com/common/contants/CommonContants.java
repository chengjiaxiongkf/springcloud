package com.common.contants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonContants {
    public static final String RESULT_CODE = "resultCode";//返回标识
    public static final String RESULT_MSG = "resultMsg";//返回信息
    public static final String SUCCESS = "Y";//返回成功标识
    public static final String FAIL = "N";//返回失败标识

    /**
     *  域名路径相关
     */
    public static final String LOCATION_HTTP = "http://";//协议头
    public static final String LOCATION_HOST_DEV = "127.0.0.1";
    public static final String LOCATION_HOST_PRD = "47.95.221.169";
    public static final String LOCATION_LOGIN_URL = "/login/html/index.html";//登陆页面


    /**
     * 提示信息
     */
    public static final String MSG_SERVICE_MAX = "服务器繁忙，请稍后再试";//服务器繁忙

    /**
     * 获取服务名赋给静态变量serviceName
     * @param serviceName
     */
    public static String serviceName;//yml中配置的服务名
    @Value("${spring.application.servicename}")
    public void setServiceName(String serviceName) {
        CommonContants.serviceName = serviceName;
    }
}
