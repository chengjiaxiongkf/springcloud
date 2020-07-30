package com.redis.util;

import com.common.vo.RedisKeyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisUtils {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 设置redisKey值
     */
    private String setRedisKey(RedisKeyVO redisKey){
        return redisKey.getServiceName()+":"+redisKey.getFunctionName()+":"+redisKey.getKeyName();
    }

    /**
     * 写入缓存
     */
    public boolean set(final RedisKeyVO redisKey, Object value) {
        try {
            String key = this.setRedisKey(redisKey);
            ValueOperations operations = redisTemplate.opsForValue();
            operations.set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 写入缓存设置时效时间
     */
    public boolean set(final RedisKeyVO redisKey, Object value, Long expireTime , TimeUnit timeUnit) {
        try {
            String key = this.setRedisKey(redisKey);
            ValueOperations operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, timeUnit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 批量删除对应的value
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }
    /**
     * 批量删除key
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0){
            redisTemplate.delete(keys);
        }
    }
    /**
     * 删除对应的value
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }
    /**
     * 判断缓存中是否有对应的value
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }
    /**
     * 读取缓存
     */
    public Object get(final String key) {
        Object result;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }
    /**
     * 哈希 添加
     */
    public void hmSet(final RedisKeyVO redisKey, Object hashKey, Object value){
        String key = this.setRedisKey(redisKey);
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key,hashKey,value);
    }
    /**
     * 哈希获取数据
     */
    public Object hmGet(String key, Object hashKey){
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        return hash.get(key,hashKey);
    }
    /**
     * list添加
     */
    public void lPush(final RedisKeyVO redisKey, Object v){
        String k = this.setRedisKey(redisKey);
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k,v);
    }
    /**
     * 列表获取
     */
    public List<Object> lRange(String k, long l, long l1){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k,l,l1);
    }
    /**
     * 集合添加
     */
    public void add(final RedisKeyVO redisKey, Object value){
        String key = this.setRedisKey(redisKey);
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key,value);
    }
    /**
     * 集合获取
     */
    public Set<Object> setMembers(final RedisKeyVO redisKey){
        String key = this.setRedisKey(redisKey);
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }
    /**
     * 有序集合添加
     */
    public void zAdd(final RedisKeyVO redisKey, Object value, double scoure){
        String key = this.setRedisKey(redisKey);
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key,value,scoure);
    }
    /**
     * 有序集合获取
     */
    public Set<Object> rangeByScore(String key,double scoure,double scoure1){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }
}
