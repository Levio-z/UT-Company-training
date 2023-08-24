package com.lion.utcompanytraining.mapper.impl;


import com.lion.utcompanytraining.entity.User;
import com.lion.utcompanytraining.mapper.UserMapper;
import org.springframework.stereotype.Repository;


import java.util.Arrays;
import java.util.List;


@Repository
public class UserDaoImpl implements UserMapper {
    @Override
    public List<User> selectAll() {
        //查库操作
        return Arrays.asList(new User());
    }

    @Override
    public User getUserByCode(String usercode) {
        return new User();
    }

    @Override
    public User getUserByCode(String userCode, String name) {
        return new User();
    }

    @Override
    public String save(User user) {
        return "success";
    }

    @Override
    public String delete(User... user) {
        return null;
    }

    @Override
    public String handlerList(List<User> users) {
        return null;
    }

    final void  sync(User... user){
    }
}
