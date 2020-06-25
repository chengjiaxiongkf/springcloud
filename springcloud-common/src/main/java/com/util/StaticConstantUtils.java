package com.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StaticConstantUtils {
    public static String serviceName;//yml中配置的服务名

    @Value("${spring.application.servicename}")
    public void setServiceName(String serviceName) {
        StaticConstantUtils.serviceName = serviceName;
    }
}
