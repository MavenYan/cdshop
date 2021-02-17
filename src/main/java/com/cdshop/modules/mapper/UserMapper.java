package com.cdshop.modules.mapper;

import com.cdshop.modules.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    public User findByName(String username);
}
