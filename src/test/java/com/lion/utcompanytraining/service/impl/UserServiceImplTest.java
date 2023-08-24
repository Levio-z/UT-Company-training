package com.lion.utcompanytraining.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
//import static org.mockito.Mockito.*;
class UserServiceImplTest {
    @Mock
    com.lion.utcompanytraining.mapper.UserMapper userMapper;
    @InjectMocks
    com.lion.utcompanytraining.service.impl.UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserByCode(){
        when(userMapper.getUserByCode(anyString())).thenReturn(new com.lion.utcompanytraining.entity.User());

        com.lion.utcompanytraining.entity.User result = userServiceImpl.getUserByCode("userCode");
        Assertions.assertEquals(new com.lion.utcompanytraining.entity.User(), result);
    }

    @Test
    void testSelectAll(){
        when(userMapper.selectAll()).thenReturn(java.util.Arrays.<com.lion.utcompanytraining.entity.User>asList(new com.lion.utcompanytraining.entity.User()));

        java.util.List<com.lion.utcompanytraining.entity.User> result = userServiceImpl.selectAll();
        Assertions.assertEquals(java.util.Arrays.<com.lion.utcompanytraining.entity.User>asList(new com.lion.utcompanytraining.entity.User()), result);
    }

    @Test
    void testSave(){
        when(userMapper.save(any())).thenReturn("saveResponse");

        java.lang.String result = userServiceImpl.save(new com.lion.utcompanytraining.entity.User());
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme