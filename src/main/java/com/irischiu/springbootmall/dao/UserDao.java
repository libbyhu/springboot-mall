package com.irischiu.springbootmall.dao;

import com.irischiu.springbootmall.dto.UserRegisterRequest;
import com.irischiu.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);
    User getUserByEmail(String email);

    Integer createUser(UserRegisterRequest userRegisterRequest);
}