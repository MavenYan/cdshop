package com.cdshop.modules.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Redis服务类
 *
 * @anthor wangjn
 * @date 2020-12-31
 */
public interface RedisService {

    /**
     * findByKey
     *
     * @param key
     * @param pageable
     * @return
     */
    Page findByKey(String key, Pageable pageable);

    /**
     * 查询验证码的值
     *
     * @param key
     * @return
     */
    String getCodeValue(String key);

    /**
     * 保存验证码的结果
     *
     * @param key
     * @param value
     */
    void saveCode(String key, Object value);

    /**
     * 删除指定key
     *
     * @param key
     */
    void delete(String key);

    /**
     * 清除缓存
     */
    void flush();
}
