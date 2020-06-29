package com.redis;

import com.util.RedisKey;
import com.util.RedisUtils;
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
            System.out.println(redisUtils.get(redisUtils.getRedisKey("REDISTEST","TESTA")));
        } catch (RuntimeException e) {
            System.out.println(4);
        }
    }
}
