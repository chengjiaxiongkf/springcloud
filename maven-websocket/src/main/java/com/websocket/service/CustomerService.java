package com.websocket.service;

import com.common.vo.websocket.CustomerVO;
import org.redisson.api.RList;

import java.util.List;

/**
 * @Author chengjiaxiong
 * @Date 2020/8/7 11:43
 */
public interface CustomerService {
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
     * 获取帮助菜单
     * @return
     */
    List<String> getAutoReplyMenu();

    /**
     * 获取回复
     */
    String getReply(String title);
}
