package com.cdshop.modules.service.impl;

import com.cdshop.modules.mapper.UserMapper;
import com.cdshop.modules.model.User;
import com.cdshop.modules.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public User login(String username) {
        return userMapper.login(username);
    }
}
