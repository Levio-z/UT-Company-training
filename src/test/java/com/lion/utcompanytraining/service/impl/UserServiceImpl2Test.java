package com.lion.utcompanytraining.service.impl;

import com.lion.utcompanytraining.entity.User;
import com.lion.utcompanytraining.mapper.UserMapper;
import com.lion.utcompanytraining.service.SyncService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class UserServiceImpl2Test {
    @Mock
    UserMapper userMapper;
    @Mock
    SyncService syncService;
    @Mock
    FinalServiceImpl finalService;
    @InjectMocks
    UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserByCode() {
        when(userMapper.getUserByCode(anyString())).thenReturn(new User("name", "sysUserCode"));

        User result = userServiceImpl.getUserByCode("userCode");
        Assertions.assertEquals(new User("name", "sysUserCode"), result);
    }

    @Test
    void testSelectAll() {
        when(userMapper.selectAll()).thenReturn(Arrays.<User>asList(new User("name", "sysUserCode")));

        List<User> result = userServiceImpl.selectAll();
        Assertions.assertEquals(Arrays.<User>asList(new User("name", "sysUserCode")), result);
    }

    @Test
    void testSave() {
        when(userMapper.save(any())).thenReturn("saveResponse");

        String result = userServiceImpl.save(new User("name", "sysUserCode"));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testDelete() {
        when(userMapper.delete(anyVararg())).thenReturn("deleteResponse");

        String result = userServiceImpl.delete(new User("name", "sysUserCode"));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testTestFinal() {
        userServiceImpl.testFinal(new User("name", "sysUserCode"));
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme