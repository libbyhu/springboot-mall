package com.irischiu.springbootmall.service;

import com.irischiu.springbootmall.dto.UserLoginRequest;
import com.irischiu.springbootmall.dto.UserRegisterRequest;
import com.irischiu.springbootmall.model.User;

public interface UserService {

    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);

    User login(UserLoginRequest userLoginRequest);
}