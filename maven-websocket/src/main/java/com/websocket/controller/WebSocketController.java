package com.websocket.controller;

import com.common.contants.websocket.WebSocketContants;
import com.common.util.ApplicationContextUtils;
import com.redis.util.RedisUtils;
import com.redis.util.RedissonUtils;
import com.websocket.beanconfig.HttpSessionConfigurator;
import com.websocket.common.WebSocketInterfaceParent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @Author chengjiaxiong
 * @Date 2020/8/6 15:05
 */
@Slf4j
@ServerEndpoint(value = "/webSocket/{name}" , configurator = HttpSessionConfigurator.class)
@Component
public class WebSocketController {

    @Autowired
    private RedissonUtils redissonUtils;
    @Autowired
    private RedisUtils redisUtils;

    private String name = "";
    /**
     * 连接成功方法
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config, @PathParam("name")String name){
        log.info(WebSocketContants.MSG_CUSTOMER_OPEN_START);
        this.name = name;
        WebSocketInterfaceParent websocketInterfaceParent = (WebSocketInterfaceParent)ApplicationContextUtils.getBean(name+"ServiceImpl");
        websocketInterfaceParent.onOpen(session,config);
        log.info(WebSocketContants.MSG_CUSTOMER_OPEN_SUCCESS);
    }

    /**
     * 关闭
     */
    @OnClose
    public void onClose(){
        log.info(WebSocketContants.MSG_CUSTOMER_CLOSE_START);
        WebSocketInterfaceParent websocketInterfaceParent = (WebSocketInterfaceParent)ApplicationContextUtils.getBean(name+"ServiceImpl");
        websocketInterfaceParent.onClose();
        log.info(WebSocketContants.MSG_CUSTOMER_CLOSE_SUCCESS);
    }

    /**
     * 异常
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info(session.getId()+":"+WebSocketContants.MSG_CUSTOMER_ERROR_START);
        WebSocketInterfaceParent websocketInterfaceParent = (WebSocketInterfaceParent)ApplicationContextUtils.getBean(name+"ServiceImpl");
        websocketInterfaceParent.onError(session,error);
        log.info(session.getId()+":"+WebSocketContants.MSG_CUSTOMER_ERROR_SUCCESS);
    }

    /**
     * 接收消息方法
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session){
        log.info("从"+session.getId()+"接收到message:"+message);
        WebSocketInterfaceParent websocketInterfaceParent = (WebSocketInterfaceParent)ApplicationContextUtils.getBean(name+"ServiceImpl");
        websocketInterfaceParent.onMessage(message,session);
    }
}