package com.websocket.charroom.controller;

import com.common.contants.websocket.WebSocketContants;
import com.common.util.ApplicationContextUtils;
import com.common.vo.websocket.ChatRoomVO;
import com.redis.util.RedisUtils;
import com.redis.util.RedissonUtils;
import com.websocket.beanconfig.HttpSessionConfigurator;
import com.websocket.charroom.service.CharRoomService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;

/**
 * @Author chengjiaxiong
 * @Date 2020/8/6 15:05
 */
@Slf4j
@ServerEndpoint(value = "/webSocket/charRoom" , configurator = HttpSessionConfigurator.class)
@Component
public class WebSocketController {

    @Autowired
    private RedissonUtils redissonUtils;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private CharRoomService webSocketService;
    private Session session;

    /**
     * 连接成功方法
     * @param session
     */
    @OnOpen
    public void onOpen(Session session,EndpointConfig config){
        this.session = session;
        log.info(WebSocketContants.MSG_OPEN_START);
        if(null==webSocketService){
            webSocketService = ApplicationContextUtils.getBean(CharRoomService.class);
        }
        webSocketService.sendMessage(session,WebSocketContants.MSG_OPEN_START);
        //获取http协议session
        HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        String userId = httpSession.getId();
        log.info("userId:"+userId+WebSocketContants.MSG_OPEN_START);
        if(StringUtils.isEmpty(userId)){//用户未登录
            try {
                session.close();//关闭此连接
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //获取所有A房间在线列表
        RList<ChatRoomVO> rList = webSocketService.getOnlineAllByName("A");
        if(rList.size()>0){//有人在线
            for (ChatRoomVO chatRoomVO : rList) {
                if (userId.equals(chatRoomVO.getUserId())) {//用户已在线
                    webSocketService.sendMessage(session, "用户已在别处登录,禁止多地登录");
                    try {
                        session.close();//关闭此连接
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
        }
        //封装ChatRoomVO
        ChatRoomVO chatRoomVO = new ChatRoomVO();
        chatRoomVO.setUserId(userId);
        chatRoomVO.setSessionId(session.getId());
        chatRoomVO.setCreatedData(new Date());
        rList.add(chatRoomVO);
        log.info(userId+":"+WebSocketContants.MSG_OPEN_SUCCESS);
        webSocketService.sendMessage(session,userId+":"+WebSocketContants.MSG_OPEN_SUCCESS+WebSocketContants.MSG_ONLINE_COUNT+rList.size());
    }

    /**
     * 关闭
     */
    @OnClose
    public void onClose(){
        log.info(this.session.getId()+":"+WebSocketContants.MSG_CLOSE_START);
        //获取已连接列表
        RList<ChatRoomVO> rList = webSocketService.getOnlineAllByName("A");
        for(int i=0;i<rList.size();i++){
            ChatRoomVO chatRoomVO = rList.get(i);
            if(this.session.getId().equals(chatRoomVO.getSessionId())){
                rList.fastRemove(i);//删除
                break;
            }
        }
        log.info(this.session.getId()+":"+WebSocketContants.MSG_CLOSE_SUCCESS);
    }

    /**
     * 异常
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info(session.getId()+":"+WebSocketContants.MSG_ERROR_START);
        //获取已连接列表
        RList<ChatRoomVO> rList = webSocketService.getOnlineAllByName("A");
        for(int i=0;i<rList.size();i++){
            ChatRoomVO chatRoomVO = rList.get(i);
            if(session.getId().equals(chatRoomVO.getSessionId())){
                rList.fastRemove(i);//删除
                break;
            }
        }
        log.info(session.getId()+":"+WebSocketContants.MSG_ERROR_SUCCESS);
    }

    /**
     * 接收消息方法
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session){
        log.info("从"+session.getId()+"接收到message:"+message);
    }
}