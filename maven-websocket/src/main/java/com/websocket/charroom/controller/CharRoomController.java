package com.websocket.charroom.controller;

import com.common.contants.CommonContants;
import com.redis.util.RedisUtils;
import com.redis.util.RedissonUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.Session;
import java.util.concurrent.TimeUnit;

/**
 * @Author chengjiaxiong
 * @Date 2020/8/6 11:10
 */
@Slf4j
@RestController
@RequestMapping("charRoom")
public class CharRoomController {

    @Autowired
    private RedissonUtils redissonUtils;
    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping(value = "/getCharRoom",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getCharRoom(){
        RLock lock = redissonUtils.getRLock("123");
        try {
            //最多等待2秒，加锁后3秒自动解锁
            if(!lock.tryLock(2,3, TimeUnit.SECONDS)){
                return CommonContants.MSG_SERVICE_MAX;
            }
            redisUtils.set("charRoomCount","123",120L);
        } catch (Exception e) {
            log.error("lock error",e);
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return "123";
    }

    @RequestMapping(value = "/setList",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String setList(){
        RSet<Session> rset = redissonUtils.getRSet("TEST:XX");
        for(int i=0;i<10;i++){
            Session session = null;
            rset.add(session);
        }
        for (Session s : rset) {
            System.out.println(s);
        }
        System.out.println("set的大小为："+rset.size()+"\n");
        return "123";
    }
}
