package com.cdshop.modules.system.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 操作Redis
 *
 * @author wangjn
 */
@Component
public class RedisUtil {

    private RedisTemplate<Object, Object> redisTemplate;
    @Value("${jwt.online-key}")
    private String key;

    public RedisUtil(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 判断Key是否存在
     * @param key 键
     * @return true:存在 false:不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 从缓存中获取指定key的值
     * @param key key
     * @return
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 将数据放入redis
     *
     * @param key 键
     * @param value 值
     * @return true:保存成功 false:保存失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据保存并设置生效时间
     *
     * @param key 键
     * @param value 值
     * @param time 生效时间(秒),time小于0则设为无限期保存
     * @return true:成功 false:失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time < 0) {
                set(key, value);
            } else {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 从缓存中删除指定key
     *
     * @param key 可以传一个或多个值
     */
    public void del(String... key) {
        if (key.length == 1) {
            redisTemplate.delete(key[0]);
        } else {
            redisTemplate.delete(CollectionUtils.arrayToList(key));
        }
    }

    /**
     * 查找匹配的key
     *
     * @param key
     * @return
     */
    public List<String> scan(String key) {
        ScanOptions options = ScanOptions.scanOptions().match(key).build();
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        RedisConnection connection = Objects.requireNonNull(factory).getConnection();
        Cursor<byte[]> cursor = connection.scan(options);
        List<String> result = new ArrayList<String>();
        while (cursor.hasNext()) {
            result.add(new String(cursor.next()));
        }
        try {
            RedisConnectionUtils.releaseConnection(connection, factory, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
