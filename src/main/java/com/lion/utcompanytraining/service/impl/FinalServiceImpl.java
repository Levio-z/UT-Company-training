package com.lion.utcompanytraining.service.impl;

import com.lion.utcompanytraining.entity.User;
import com.lion.utcompanytraining.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class  FinalServiceImpl{
    @Resource
    private UserMapper userMapper;
    public final void method(User user){
    }
    public static String check(User user){
return null;
    }
    public User getUserByCode(String userCode){
        return userMapper.getUserByCode(userCode);
    }
}
