package com.lion.utcompanytraining.mapper;


import com.lion.utcompanytraining.entity.User;

import java.util.List;

public interface UserDao {
    List<User> selectAll();

    User getOne();

    String save(User appUser);

}
