package com.websocket.service.Impl;

import com.common.contants.websocket.WebSocketContants;
import com.common.vo.websocket.CustomerVO;
import com.common.vo.websocket.WebSocketVO;
import com.redis.contants.RedisContants;
import com.redis.util.RedissonUtils;
import com.websocket.common.WebSocketInterfaceParent;
import com.websocket.service.CharRoomService;
import com.websocket.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RList;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 多人在线聊天室
 * @Author chengjiaxiong
 * @Date 2020/8/8 9:41
 */
@Slf4j
@Service
public class CharRoomServiceImpl extends WebSocketInterfaceParent implements CharRoomService {
    @Autowired
    private RedissonUtils redissonUtils;

    /**
     * 建立连接
     * @param session
     * @param config
     */
    @Override
    public void onOpen(Session session, EndpointConfig config) {
        super.session = session;
        log.info(WebSocketContants.MSG_CUSTOMER_OPEN_START);
        //获取http协议session
        HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        String userId = httpSession.getId();
        log.info("userId:"+userId+WebSocketContants.MSG_CUSTOMER_OPEN_START);
        if(StringUtils.isEmpty(userId)){//用户未登录
            try {
                session.close();//关闭此连接
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //获取所有A房间在线列表
        RList<CustomerVO> rList = this.getOnlineAll();
        if(rList.size()>0){//有人在线
            for (CustomerVO customerVO : rList) {
                if (userId.equals(customerVO.getUserId())) {//用户已在线
                    this.sendMessage(session, "用户已在别处登录,禁止多地登录");
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
        CustomerVO customerVO = new CustomerVO();
        customerVO.setUserId(userId);
        customerVO.setSessionId(session.getId());
        customerVO.setCreatedData(new Date());
        this.insertOnline(rList,customerVO);
        log.info(userId+":"+WebSocketContants.MSG_CUSTOMER_OPEN_SUCCESS);
    }

    /**
     * 关闭连接
     */
    @Override
    public void onClose() {
        String closeSession = super.session.getId();
        //获取已连接列表
        RList<CustomerVO> rList = this.getOnlineAll();
        for(int i=0;i<rList.size();i++){
            WebSocketVO chatRoomVO = rList.get(i);
            if(closeSession.equals(chatRoomVO.getSessionId())){
                RLock lock = redissonUtils.getRLock(closeSession);
                try {
                    if(lock.tryLock(2,3,TimeUnit.SECONDS)){
                        rList.fastRemove(i);//删除
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
                break;
            }
        }
    }

    /**
     * 异常连接
     */
    @Override
    public void onError(Session session, Throwable error) {
        log.info(session.getId()+" error");
        this.onClose();
    }

    /**
     * 接收消息
     * @param message
     * @param session
     */
    @Override
    public void onMessage(String message, Session session) {
//        this.batchSendMessage(session,List<>);
    }

    /**
     * 批量发送消息
     * @param session
     * @param message
     */
    public void batchSendMessage(Session session, String message) {

    }

    /**
     * 发送消息
     * @param session
     * @param message
     */
    @Override
    public void sendMessage(Session session, String message) {
        try {
            RLock lock = redissonUtils.getRLock(session.getId());
            if(lock.tryLock(2L,3L, TimeUnit.SECONDS)){
                log.info("向客户端发送数据：" + message);
                session.getBasicRemote().sendText(message);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            log.error(""+session.getId()+" send error.",e);
        }
    }

    /**
     * 在线列表
     * @return
     */
    @Override
    public RList<CustomerVO> getOnlineAll() {
        //获取已连接列表
        RList<CustomerVO> list = redissonUtils.getRList(RedisContants.CUSTOMER_SESSIONS);
        return list;
    }

    /**
     * 插入连接列表
     * @param rList
     * @param customerVO
     */
    @Override
    public void insertOnline(RList<CustomerVO> rList,CustomerVO customerVO) {
        rList.add(customerVO);
    }
}
