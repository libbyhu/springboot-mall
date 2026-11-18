package com.irischiu.springbootmall.service.impl;

import com.irischiu.springbootmall.dao.UserDao;
import com.irischiu.springbootmall.dto.UserRegisterRequest;
import com.irischiu.springbootmall.model.User;
import com.irischiu.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }
}