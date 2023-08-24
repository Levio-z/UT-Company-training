package com.lion.utcompanytraining.mapper;


import com.lion.utcompanytraining.entity.User;

import java.util.List;

public interface UserMapper {
    List<User> selectAll();

    User getUserByCode(String userCode);

    User getUserByCode(String userCode,String name);

    String save(User appUser);

    String delete(User...user);

    String handlerList(List<User> users);

}
