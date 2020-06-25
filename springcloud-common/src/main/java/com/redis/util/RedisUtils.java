package com.redis.util;

import com.redis.dto.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
@Service
public class RedisUtils {
    @Autowired
    private RedisTemplate redisTemplate;

    public String setRedisKey(RedisKey redisKey){
        StringBuffer sb = new StringBuffer();
        sb.append(redisKey.getServiceName());
        sb.append(":");
        sb.append(redisKey.getFunctionName());
        sb.append(":");
        sb.append(redisKey.getKeyName());
        return sb.toString();
    }

    /**
     * 写入缓存
     * @param redisKey
     * @param value
     * @return
     */
    public boolean set(final RedisKey redisKey, Object value) {
        boolean result = false;
        try {
            String key = this.setRedisKey(redisKey);
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 写入缓存设置时效时间
     * @param redisKey
     * @param value
     * @return
     */
    public boolean set(final RedisKey redisKey, Object value, Long expireTime ,TimeUnit timeUnit) {
        boolean result = false;
        try {
            String key = this.setRedisKey(redisKey);
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 批量删除对应的value
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }
    /**
     * 批量删除key
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0){
            redisTemplate.delete(keys);
        }
    }
    /**
     * 删除对应的value
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }
    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }
    /**
     * 读取缓存
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }
    /**
     * 哈希 添加
     * @param redisKey
     * @param hashKey
     * @param value
     */
    public void hmSet(final RedisKey redisKey, Object hashKey, Object value){
        String key = this.setRedisKey(redisKey);
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key,hashKey,value);
    }
    /**
     * 哈希获取数据
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGet(String key, Object hashKey){
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        return hash.get(key,hashKey);
    }
    /**
     * @param redisKey
     * @param v
     */
    public void lPush(final RedisKey redisKey,Object v){
        String k = this.setRedisKey(redisKey);
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k,v);
    }
    /**
     * 列表获取
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List<Object> lRange(String k, long l, long l1){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k,l,l1);
    }
    /**
     * 集合添加
     * @param redisKey
     * @param value
     */
    public void add(final RedisKey redisKey,Object value){
        String key = this.setRedisKey(redisKey);
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key,value);
    }
    /**
     * 集合获取
     * @param redisKey
     * @return
     */
    public Set<Object> setMembers(final RedisKey redisKey){
        String key = this.setRedisKey(redisKey);
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }
    /**
     * 有序集合添加
     * @param redisKey
     * @param value
     * @param scoure
     */
    public void zAdd(final RedisKey redisKey,Object value,double scoure){
        String key = this.setRedisKey(redisKey);
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key,value,scoure);
    }
    /**
     * 有序集合获取
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    public Set<Object> rangeByScore(String key,double scoure,double scoure1){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }
}
