package com.websocket.service;

import com.common.vo.websocket.CustomerVO;
import org.redisson.api.RList;

import javax.websocket.Session;

/**
 * 多人在线聊天室服务
 * @Author chengjiaxiong
 * @Date 2020/8/8 9:37
 */
public interface CharRoomService {
    /**
     * 获取在线列表
     * @return
     */
    RList<CustomerVO> getOnlineAll();

    /**
     * 插入在线信息
     * @param rList
     * @param customerVO
     */
    void insertOnline(RList<CustomerVO> rList,CustomerVO customerVO);

    /**
     * 批量发送消息
     * @param session
     * @param message
     */
    void batchSendMessage(Session session, String message);
}
