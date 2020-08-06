package com.common.vo.redis;

import com.common.contants.CommonContants;
import org.apache.commons.lang.StringUtils;

public class RedisKeyVO {
    String serviceName;
    String functionName;
    String keyName;

    /**
     * redis命名规范化
     */
    public RedisKeyVO(String functionName, String keyName){
        String serviceName = CommonContants.serviceName;
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
