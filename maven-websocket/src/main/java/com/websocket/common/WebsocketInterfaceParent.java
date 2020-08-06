package com.websocket.common;

import javax.websocket.EndpointConfig;
import javax.websocket.Session;

/**
 * @Author chengjiaxiong
 * @Date 2020/8/6 20:31
 */
public abstract class WebsocketInterfaceParent {
    /**
     * 打开连接
     * @param session
     * @param config
     */
    public abstract void onOpen(Session session, EndpointConfig config);

    /**
     * 关闭连接
     */
    public abstract void onClose();

    /**
     * 连接异常
     * @param session
     * @param error
     */
    public abstract void onError(Session session, Throwable error);

    /**
     * 接受消息
     * @param message
     * @param session
     */
    public abstract void onMessage(String message, Session session);
}
