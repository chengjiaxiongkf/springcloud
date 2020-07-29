package com.common.contants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonContants {
    public static final String RESULT_CODE = "resultCode";//返回标识
    public static final String RESULT_MSG = "resultMsg";//返回信息
    public static final String SUCCESS = "Y";//返回成功标识
    public static final String FAIL = "N";//返回失败标识
    public static String serviceName;//yml中配置的服务名

    /**
     * 获取服务名赋给静态变量serviceName
     * @param serviceName
     */
    @Value("${spring.application.servicename}")
    public void setServiceName(String serviceName) {
        CommonContants.serviceName = serviceName;
    }
}
