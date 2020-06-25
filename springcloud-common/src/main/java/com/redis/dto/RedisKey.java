package com.redis.dto;

import com.util.StaticConstantUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

public class RedisKey {
    String serviceName;
    String functionName;
    String keyName;

    /**
     * 规范化redis命名
     * @param functionName
     * @param keyName
     */
    public RedisKey(String functionName,String keyName) throws RuntimeException{
        String serviceName = StaticConstantUtils.serviceName;
        if(StringUtils.isEmpty(serviceName)){
            throw new RuntimeException("请检查yml配置文件中是否配置serviceName.");
        }
        String regex = "^[0-9A-Z]{1,20}+$";//大写加数字不能超过20位
        if(!functionName.matches(regex)){
            throw new RuntimeException("functionName只能包含大写跟数字并且长度在1到20位.");
        }
        if(!keyName.matches(regex)){
            throw new RuntimeException("keyName只能包含大写跟数字并且长度在1到20位.");
        }
        this.serviceName = serviceName;
        this.functionName = functionName;
        this.keyName = keyName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getKeyName() {
        return keyName;
    }
}
