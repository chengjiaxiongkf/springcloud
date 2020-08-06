package com.websocket.charroom.service.Impl;

import com.common.contants.websocket.WebSocketContants;
import com.common.util.ApplicationContextUtils;
import com.common.vo.websocket.ChatRoomVO;
import com.redis.util.RedissonUtils;
import com.websocket.charroom.service.CharRoomService;
import com.websocket.common.WebsocketInterfaceParent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import java.io.IOException;

/**
 * @Author chengjiaxiong
 * @Date 2020/8/6 15:03
 */
@Slf4j
@Service
public class CharRoomServiceImpl extends WebsocketInterfaceParent implements CharRoomService {

    protected Session session;

    @Autowired
    private RedissonUtils redissonUtils;

    @Override
    public void onOpen(Session session, EndpointConfig config) {

    }

    @Override
    public void onClose() {

    }

    @Override
    public void onError(Session session, Throwable error) {

    }

    @Override
    public void onMessage(String message, Session session) {

    }

    /**
     * 发送消息的方法
     * @param session
     * @param message
     */
    public void sendMessage(Session session,String message) {
        try {
            synchronized (session) {
                log.info("向客户端发送数据：" + message);
                session.getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error(""+session.getId()+" send error.",e);
        }
    }

    /**
     * 批量发送消息的方法
     * @param set
     * @param message
     */
    @Override
    public void sendMessageBatch(RMap<String,Session> set, String message) {

    }

    /**
     * 根据房间名称获取在线列表
     * @return
     */
    @Override
    public RList<ChatRoomVO> getOnlineAllByName(String name) {
        if(null==redissonUtils){//动态的创建一个redisson实例
            redissonUtils = ApplicationContextUtils.getBean(RedissonUtils.class);
        }
        if(!StringUtils.isEmpty(name)){
            name = ":"+name;
        }
        RList<ChatRoomVO> rList = redissonUtils.getRList(WebSocketContants.REDISSON_KEY_SESSIONS+name);
        return rList;
    }

    @Override
    public void insertOnline(String userId, ChatRoomVO chatRoomVO) {
        RMap<String,ChatRoomVO> rMap = redissonUtils.getRMap(WebSocketContants.REDISSON_KEY_SESSIONS);
    }
}
