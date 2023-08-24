package com.lion.utcompanytraining.service;

import com.lion.utcompanytraining.entity.User;

import java.util.List;

public interface UserService {
    User getUserByCode(String userCode);
    List<User> selectAll();
    String save(User user);
    String delete(User...user);

    void testFinal(User user);

    void throwException() throws Exception;
}
