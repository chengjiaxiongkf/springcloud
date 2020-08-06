package com.common.contants.websocket;

/**
 * @Author chengjiaxiong
 * @Date 2020/8/6 19:06
 */
public class WebSocketContants {
    //在线连接集合的redisson
    public static final String REDISSON_KEY_SESSIONS = "SL_CHARROOM:WEBSOCKET:CHARROOM:SESSIONS";

    //-------------------返回前台提示--------------
    public static final String MSG_OPEN_REPEAT = "当前状态已连接,请不要重复连接.";
    public static final String MSG_OPEN_START = "正在连接...";
    public static final String MSG_OPEN_SUCCESS = "连接成功...";
    public static final String MSG_OPEN_FAIL = "连接失败...";
    public static final String MSG_CLOSE_START = "正在关闭...";
    public static final String MSG_CLOSE_SUCCESS = "关闭成功...";
    public static final String MSG_CLOSE_FAIL = "关闭失败...";
    public static final String MSG_ERROR_START = "正在异常关闭...";
    public static final String MSG_ERROR_SUCCESS = "异常关闭成功...";
    public static final String MSG_ERROR_FAIL = "异常关闭失败...";
    public static final String MSG_ONLINE_COUNT = "当前在线人数:";
}
