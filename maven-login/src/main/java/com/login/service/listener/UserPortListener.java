package com.login.service.listener;

import com.common.vo.RedisKeyVO;
import com.redis.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.concurrent.TimeUnit;

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
        RedisKeyVO redisKeyVO = new RedisKeyVO("LISTENER","USERPORT");
        redisUtils.set(redisKeyVO,textMessage.getText(),600L, TimeUnit.SECONDS);
        System.out.println("UserPortListener的消费任务:" + textMessage.getText());
    }
}
