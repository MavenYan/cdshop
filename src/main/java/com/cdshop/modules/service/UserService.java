package com.cdshop.modules.service;

import com.cdshop.modules.model.User;

public interface UserService {

    /**
     * 用户登录
     * @param username
     * @return
     */
    public User login(String username);
}
