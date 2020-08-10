package com.websocket.service.Impl;

import com.common.contants.websocket.WebSocketContants;
import com.common.vo.websocket.CustomerVO;
import com.common.vo.websocket.WebSocketVO;
import com.redis.contants.RedisContants;
import com.redis.util.RedissonUtils;
import com.websocket.service.CustomerService;
import com.websocket.common.WebSocketInterfaceParent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Date;
import java.util.List;
/**
 * 在线客服
 * @Author chengjiaxiong
 * @Date 2020/8/7 11:41
 */
@Slf4j
@Service
public class CustomerServiceImpl extends WebSocketInterfaceParent implements CustomerService {
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
        //获取已连接列表
        RList<CustomerVO> rList = this.getOnlineAll();
        for(int i=0;i<rList.size();i++){
            WebSocketVO chatRoomVO = rList.get(i);
            if(super.session.getId().equals(chatRoomVO.getSessionId())){
                rList.fastRemove(i);//删除
                break;
            }
        }
    }

    /**
     * 异常连接
     */
    @Override
    public void onError(Session session, Throwable error) {
        //获取已连接列表
        RList<CustomerVO> rList = this.getOnlineAll();
        for(int i=0;i<rList.size();i++){
            WebSocketVO chatRoomVO = rList.get(i);
            if(session.getId().equals(chatRoomVO.getSessionId())){
                rList.fastRemove(i);//删除
                break;
            }
        }
    }

    /**
     * 接收消息
     * @param message
     * @param session
     */
    @Override
    public void onMessage(String message, Session session) {
        this.sendMessage(session,this.getReply(message));
    }

    /**
     * 发送消息
     * @param session
     * @param message
     */
    @Override
    public void sendMessage(Session session, String message) {
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

    @Override
    public List<String> getAutoReplyMenu() {
        return redissonUtils.getRList(RedisContants.CUSTOMER_AUTOREPLY_MENU);
    }

    @Override
    public String getReply(String title) {
        if("help".equals(title)){
            List list = this.getAutoReplyMenu();
            if(!CollectionUtils.isEmpty(list)){
                return list.toString();
            }
        }
        RMap<String,String> replyMap = redissonUtils.getRMap(RedisContants.CUSTOMER_AUTOREPLY_CONTEXT);
        String reply = MapUtils.getString(replyMap,title);
        if(StringUtils.isEmpty(reply)){
            reply = "不好意思，未能识别您的问题.";
        }
        return reply;
    }
}
