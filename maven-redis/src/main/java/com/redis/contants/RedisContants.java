package com.redis.contants;

/**
 * @Author chengjiaxiong
 * @Date 2020/8/10 8:09
 */
public class RedisContants {
    /**
     * [websocket] redis Keys
     */
    //客户服务连接集合redissonKey
    public static final String CUSTOMER_SESSIONS  = "SL_WEBSOCKET:CUSTOMER:SESSIONS";
    //客户服务帮助菜单
    public static final String CUSTOMER_AUTOREPLY_MENU = "SL_WEBSOCKET:CUSTOMER:AUTOREPLY:MENU";
    //客户服务自动回复列表
    public static final String CUSTOMER_AUTOREPLY_CONTEXT = "SL_WEBSOCKET:CUSTOMER:AUTOREPLY:CONTEXT";


    /**
     * [login] Redis keys
     */
    public static final String REDIS_LOGIN_TOKEN = "SL_LOGIN:TOKEN:";
    public static final String REDIS_LOGIN_USER = "SL_LOGIN:USER:";
}
