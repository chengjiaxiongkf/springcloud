package com.redis;

import com.redis.dto.RedisKey;
import com.redis.util.RedisUtils;
import org.junit.Test;
import com.TmallApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisTest extends TmallApplicationTests {

    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void setValue(){
        redisUtils.set(new RedisKey("REDISTEST","TESTA"),"123");
        System.out.println("------end");
    }

    @Test
    public void getValue(){
        try {
            new RedisKey("是我是","是我是");
        } catch (RuntimeException e) {
            System.out.println(1);
        }
        try {
            new RedisKey("12QWEQWasda","12QWEQWasda");
        } catch (RuntimeException e) {
            System.out.println(2);
        }
        try {
            new RedisKey("QWEQWEQWEQWEQW131231231231231","QWEQWEQWEQWEQW131231231231231");
        } catch (RuntimeException e) {
            System.out.println(3);
        }
        try {
            new RedisKey("QWEQWEQ123QQWEWWQW1W","QWEQWEQ123QQWEWWQW1W");
        } catch (RuntimeException e) {
            System.out.println(4);
        }
    }
}