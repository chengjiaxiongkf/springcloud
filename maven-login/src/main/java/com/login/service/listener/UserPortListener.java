package com.login.service.listener;

import com.common.vo.redis.RedisKeyVO;
import com.redis.util.RedisUtils;
import com.redis.util.RedissonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * @Author chengjiaxiong
 * @Date 2020/8/3 23:02
 */
@Component
public class UserPortListener {
    @Autowired
    private RedisUtils redisUtils;
    @JmsListener(destination = "UserPort")
    public void onMessage(Message message) throws JMSException {
        TextMessage textMessage = (TextMessage) message;
        redisUtils.set("SL_USER_LISTENER_USERPORT",textMessage.getText());
        System.out.println("UserPortListener的消费任务:" + textMessage.getText());
    }
}
