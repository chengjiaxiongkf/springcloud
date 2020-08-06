package com.websocket.charroom.service;

import com.common.vo.ResultVO;
import com.common.vo.websocket.ChatRoomVO;
import com.websocket.common.WebsocketInterfaceParent;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.springframework.stereotype.Service;

import javax.websocket.Session;

/**
 * @Author chengjiaxiong
 * @Date 2020/8/6 15:05
 */
public interface CharRoomService {
    /**
     * 发送消息的方法
     * @param session
     */
    void sendMessage(Session session,String message);

    /**
     * 批量发送消息的方法
     * @param set
     * @param message
     */
    void sendMessageBatch(RMap<String,Session> set, String message);

    /**
     * 获取在线列表
     * @param name
     * @return
     */
    RList<ChatRoomVO> getOnlineAllByName(String name);

    void insertOnline(String userId,ChatRoomVO chatRoomVO);
}
