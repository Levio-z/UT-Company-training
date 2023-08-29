package com.lion.utcompanytraining.service.impl;

import com.lion.utcompanytraining.entity.User;
import com.lion.utcompanytraining.mapper.UserMapper;
import com.lion.utcompanytraining.service.SyncService;
import com.lion.utcompanytraining.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private SyncService syncService;

    @Resource
    private FinalServiceImpl finalService;

    private static final String XX ="Xxx";

    @Override
    public User getUserByCode(String userCode) {
        return userMapper.getUserByCode(userCode);
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public String save(User user) {
        // 同步数据
        syncService.sync(user);
        return userMapper.save(user);
    }

    @Override
    public String delete(User...user) {
        return userMapper.delete(user);
    }

    @Override
    public void testFinal(User user) {
        finalService.method(user);
    }

    @Override
    public void throwException() throws Exception {
        throw new Exception("aaa");
    }


}
