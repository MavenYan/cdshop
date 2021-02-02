package com.cdshop.modules.service.impl;

import com.alibaba.druid.sql.PagerUtils;
import com.cdshop.modules.monitor.dto.RedisVo;
import com.cdshop.modules.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    RedisTemplate redisTemplate;
    @Value("${loginCode.effective-time}")
    private long effective;

    @Override
    public Page findByKey(String key, Pageable pageable) {
        List<RedisVo> redisVos = new ArrayList<RedisVo>();
        if (!"*".equals(key)) {
            key = "*" + key + "*";
        }
        for (Object o : redisTemplate.keys(key)) {
            if (o.toString().indexOf("role::loadPermissionByUser") != -1
                    || o.toString().indexOf("user::loadUserByUsername") != -1) {
                continue;
            }
            DataType dataType = redisTemplate.type(o.toString());
            if (!"string".equals(dataType.code())) {
                continue;
            }
            RedisVo redisVo = new RedisVo(o.toString(), redisTemplate.opsForValue().get(o.toString()).toString());
            redisVos.add(redisVo);
        }
        return null;
    }

    @Override
    public String getCodeValue(String key) {
        try {
            String value = redisTemplate.opsForValue().get(key).toString();
            return value;
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void saveCode(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, effective, TimeUnit.MINUTES);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void flush() {
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }
}
