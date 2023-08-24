package com.lion.utcompanytraining.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.lion.utcompanytraining.entity.User;
import com.lion.utcompanytraining.mapper.UserMapper;
import com.lion.utcompanytraining.service.SyncService;
import com.lion.utcompanytraining.util.ResourceHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.mockito.Mockito.*;

class UserServiceImplTest {
    // 步骤一：定义对象阶段
    // ②模拟依赖对象（类成员）
    @Mock
    UserMapper userMapper;

    @Mock
    SyncService syncService;

    @Mock
    private FinalServiceImpl finalService;

    // 步骤一：定义对象阶段
    // ①定义测试对象
    // ③注入依赖对象（通过@InjectMocks）
    @InjectMocks
//    @Spy 加上注解就可以模拟自己部分方法
    UserServiceImpl userServiceImpl;

    /** 定义静态常量 */
    /**
     * 资源路径
     */
    private static final String RESOURCE_PATH = "/userServiceImplTest/";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(finalService, "userMapper", userMapper);
    }

    /**
     * 构建User对象
     *
     * @return
     */
    private User buildUser() {
        String path = RESOURCE_PATH;
        String text = ResourceHelper.getResourceAsString(getClass(), path + "User.json");
        User buildUser = JSON.parseObject(text, User.class);
        return buildUser;
    }

    /**
     * 构建User对象
     *
     * @return
     */
    private  User buildUser1() {
        String path = RESOURCE_PATH;
        String text = ResourceHelper.getResourceAsString(getClass(), path + "User1.json");
        User buildUser = JSON.parseObject(text, User.class);
        return buildUser;
    }



    @Test
    @DisplayName("测试：根据用户code获取用户对象-成功")
    void testGetUserByCode() {
        // 步骤二：模拟方法阶段
        // ④模拟依赖方法
        // ②模拟依赖对象（参数或返回值）
        // 参数：anyString()
        // 返回值：buildUser()
        Mockito.when(userMapper.getUserByCode(anyString())).thenReturn(buildUser1());

        // 步骤三：调用方法阶段
        // ②模拟依赖对象（参数）
        // "userCode"
        // ⑤调用测试方法
        // getUserByCode
        User result = userServiceImpl.getUserByCode("userCode");
        // 步骤三：调用方法阶段
        // ⑦验证数据对象（返回值）
        Assertions.assertEquals(buildUser1(), result);


        // 步骤四：验证方法阶段
        // ⑥验证依赖方法
        // ⑦验证数据对象（参数）
        Mockito.verify(userMapper, times(1)).getUserByCode("userCode");
        // 步骤四：验证方法阶段
        // ⑧验证依赖对象
        Mockito.verifyNoInteractions(syncService);
    }

    @Test
    @DisplayName("测试：查询所有对象")
    void testSelectAll() {
        when(userMapper.selectAll()).thenReturn(Arrays.<User>asList(new User()));
        List<User> result = userServiceImpl.selectAll();
        Assertions.assertEquals(Arrays.<User>asList(new User()), result);
    }

    @Test
    @DisplayName("测试：保存用户对象")
    void testSave() {
        Mockito.doReturn("saveResponse", "saveResponse1").when(userMapper).save(any());
        String result = userServiceImpl.save(new User());
        Assertions.assertEquals("saveResponse", result);
    }

    @Test
    @DisplayName("测试：删除用户对象")
    void testDelete() {
        Mockito.doReturn("deleteResponse").when(userMapper).delete(buildUser());
        userServiceImpl.delete(new User());
        verify(userMapper).delete(any());
    }

    @Test
    // ④模拟依赖方法
    // 4.1 根据返回模拟方法
    void testAccordingToTheReturnMockMethod() {
        // 1.模拟无返回值方法
        Mockito.doReturn("deleteResponse").when(userMapper).delete(buildUser());

        // 2.模拟单个返回值方法
        Mockito.when(userMapper.save(any())).thenReturn("saveResponse");
        Mockito.doReturn("saveResponse").when(userMapper).save(any());

        // 3.模拟调用方法多次，返回值不同
        Mockito.doReturn("saveResponse", "saveResponse1").when(userMapper).save(any());
        Mockito.when(userMapper.save(any())).thenReturn("saveResponse", "saveResponse1");

        // 4.模拟方法定制返回值
        Map<String, User> map = new HashMap<>();
        map.put("UC000", buildUser());
        map.put("UC001", buildUser());
        Answer<User> aswser = (invocation) -> {
            Object[] args = invocation.getArguments();
            User o = map.get(args[0].toString());
            return o;
        };
        Mockito.when(userMapper.getUserByCode(anyString())).thenAnswer(aswser);

        // 另外的模拟方式
        Mockito.doAnswer(aswser).when(userMapper).getUserByCode(anyString());

        // 5.模拟方法抛出异常 只能有一条，否则后续模拟会直接抛异常
        RuntimeException e = new RuntimeException("e");
        Mockito.doThrow(e).when(userMapper).getUserByCode(anyString());
        Mockito.when(userMapper.getUserByCode(anyString())).thenThrow(e);
        // 异常也可以是类
        Mockito.when(userMapper.getUserByCode(anyString())).thenThrow(RuntimeException.class);
        Mockito.doThrow(RuntimeException.class).when(userMapper).getUserByCode(anyString());

        // 6.模拟方法抛出多个异常
        Mockito.doThrow(RuntimeException.class, RuntimeException.class, RuntimeException.class).when(userMapper).getUserByCode(anyString());
        Mockito.doThrow(e, e).when(userMapper).getUserByCode(anyString());

        // 7.直接调用真实方法
        Mockito.doCallRealMethod().when(finalService).getUserByCode(anyString());
        when(finalService.getUserByCode(anyString())).thenCallRealMethod();

        Assertions.assertNotNull(new Object());
    }

    @Test
    // 4.2 根据参数模拟方法
    void testAccordingToParametersMockMethod() {
        User user = buildUser();
        User user1 = buildUser1();
        String userCode = "2";

        // 1.模拟无参数方法
        Mockito.doReturn(new ArrayList<>()).when(userMapper).selectAll();
        Mockito.when(userMapper.selectAll()).thenReturn(new ArrayList<>());

        // 2.模拟指定参数方法
        Mockito.doReturn(buildUser()).when(userMapper).getUserByCode(userCode);
        Mockito.when(userMapper.getUserByCode(userCode)).thenReturn(buildUser());

        // 3.模拟任意参数方法(不为空会调用)
        Mockito.doReturn(buildUser()).when(userMapper).getUserByCode(anyString());
        Mockito.when(userMapper.getUserByCode(anyString())).thenReturn(buildUser());

        // 4.模拟可空参数方法
        Mockito.doReturn(buildUser()).when(userMapper).getUserByCode(Mockito.nullable(String.class));
        Mockito.when(userMapper.getUserByCode(anyString(), Mockito.nullable(String.class))).thenReturn(buildUser());

        // 5.模拟必空参数方法
        Mockito.doReturn(buildUser()).when(userMapper).getUserByCode(anyString(), Mockito.isNull());
        Mockito.when(userMapper.getUserByCode(anyString(), Mockito.isNull())).thenReturn(buildUser());

        // 6.模拟不同参数方法
        Mockito.doReturn(buildUser()).when(userMapper).getUserByCode("000");
        Mockito.doReturn(buildUser1()).when(userMapper).getUserByCode("001");

        // 7.模拟可变参数方法
        // 匹配一个
        Mockito.doReturn("1").when(userMapper).delete(Mockito.any());
        // 匹配多个
        Mockito.doReturn("2").when(userMapper).delete(Mockito.<User>any());
        // 指定参数
        Mockito.doReturn("1").when(userMapper).delete(buildUser(), buildUser1());

        Assertions.assertNotNull(new Object());
    }

    @Test
    // 4.3 模拟特殊方法
    void testMockSpecialMethod () throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 1.模拟静态方法(有返回值)
        MockedStatic finalServiceMockedStatic = mockStatic(FinalServiceImpl.class);
        Mockito.when(FinalServiceImpl.check(any())).thenReturn("s");
        // 调用
        String check = finalService.check(new User());
        String check1 = FinalServiceImpl.check(any());
        finalServiceMockedStatic.close();
    }

    @Test
    // ⑤调用测试方法
    void testCallTestMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 1.直接调用
        Mockito.when(userMapper.getUserByCode(anyString())).thenReturn(buildUser1());
        User result1 = userServiceImpl.getUserByCode("userCode");

        Mockito.doReturn(buildUser()).when(userMapper).getUserByCode("000");
        // 2.私有方法
        // 获取私有方法
        Method privateMethod = FinalServiceImpl.class.getDeclaredMethod("getUserByCode", String.class);
        privateMethod.setAccessible(true); // 设置私有方法可访问
        // 调用私有方法
        User result = (User) privateMethod.invoke(finalService, "000");
        Assertions.assertNotNull(result);
    }

    @Test
    // ⑥验证依赖方法
    // 6.1 根据参数验证依赖方法调用
    void testValidateDependentMethodCallsBasedOnParameters() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        User user = new User();
        // 1.验证无参方法调用
        Mockito.verify(userMapper).selectAll();

        // 2.验证指定参数方法调用
        Mockito.verify(userMapper).save(user);

        // 3.验证任意参数方法调用
        Mockito.verify(userMapper).save(any());

        // 4.验证可空参数方法调用
        Mockito.verify(userMapper).getUserByCode(anyString(), Mockito.nullable(String.class));

        // 5.验证必空参数方法调用
        Mockito.verify(userMapper).getUserByCode(anyString(), Mockito.isNull());

        // 6.验证可变参数方法调用
        Mockito.verify(userMapper).delete(Mockito.any());
        //匹配一个
        Mockito.verify(userMapper).delete(Mockito.<User>any());
        //匹配多个
        Mockito.verify(userMapper).delete(buildUser(), buildUser1());
    }
    // 6.2 验证方法调用次数
    @Test
    void testVerifyTheNumberOfMethodCalls() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        User user = new User();

        // 1.验证方法默认调用1次
        Mockito.verify(userMapper).delete(Mockito.any());

        // 2.验证方法从不调用
        Mockito.verify(userMapper, Mockito.never()).delete(Mockito.any());

        // 3.验证方法调用n次
        Mockito.verify(userMapper, Mockito.times(2)).delete(Mockito.any());

        // 4.验证方法调用至少1次
        Mockito.verify(userMapper, Mockito.atLeastOnce()).delete(Mockito.any());

        // 5.验证方法调用至少n次
        Mockito.verify(userMapper, Mockito.atLeast(3)).delete(Mockito.any());

        // 6.验证方法调用最多1次
        Mockito.verify(userMapper, Mockito.atMostOnce()).delete(Mockito.any());

        // 7.验证方法调用最多n次
        Mockito.verify(userMapper, Mockito.atMost(3)).delete(Mockito.any());

        // 8.验证方法调用1次
        Mockito.verify(userMapper, Mockito.only()).delete(Mockito.any());

        // 9.验证方法调用1次,calls只配合用于验证模拟对象的方法调用顺序
        InOrder inOrder = inOrder(userMapper);
        inOrder.verify(userMapper, Mockito.calls(1)).delete(Mockito.any());

    }

    // 2.使用@Captor注解定义捕获器
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;
    //6.3 验证方法调用并捕获参数
    @Test
    void testValidateMethodCallsAndCaptureParameters() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 1. 使用ArgumentCaptor.forClass方法定义捕获器,捕获多次
        Mockito.doReturn("String").when(userMapper).delete(buildUser());
        userServiceImpl.delete(buildUser());
        userServiceImpl.delete(buildUser());
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userMapper, times(2)).delete(userArgumentCaptor.capture());
        List<User> allValues = userArgumentCaptor.getAllValues();
        System.out.println(allValues);
        // 2. 使用ArgumentCaptor.forClass方法定义捕获器,捕获1次
        Mockito.doReturn("String").when(userMapper).save(buildUser());
        userServiceImpl.save(buildUser());
        ArgumentCaptor<User> userArgumentCaptor2 = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userMapper, times(1)).save(userArgumentCaptor2.capture());
        User value = userArgumentCaptor2.getValue();
        System.out.println(value);
    }
    // ⑦验证数据对象
    @Test
    void testWithAnswer8() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        User user = new User();
        user.setName("a");
        Boolean b = Boolean.TRUE;
        User user2 = new User();
        user2.setName("a");
        List<User> list1 = Arrays.asList(user);
        List<User> list2 = Arrays.asList(user2);

        String[] array = new String[]{"1", "2", "3"};
        String[] array2 = new String[]{"1", "2", "3"};
        List<String> list = new ArrayList<>();
        String text = "[{\"name\":\"a\"}]";

        //①验证对象空值
        //1. 验证对象为空
        Assertions.assertNull(user, "用户不能为空");

        //2. 验证对象不为空
        Assertions.assertNotNull(user, "用户不能为空");

        //②验证数据对象布尔值

        //1. 验证数据对象为假
        Assertions.assertFalse(b, "返回值必须为假");

        //2. 验证数据对象为真
        Assertions.assertTrue(b, "返回值必须为真");

        //③验证对象引用
        //1. 验证数据对象一致
        Assertions.assertSame(user, user2, "用户必须一致");

        //2. 验证数据对象不一致
        Assertions.assertNotSame(user, user2, "用户必须不一致");

        //④验证数据对象值
        //1. 验证简单数据对象
        Assertions.assertNotEquals(user, user2, "用户必须不相等");
        Assertions.assertEquals(user, user2, "用户必须相等");

        //2. 验证简单数组和集合对象
        Assertions.assertArrayEquals(array, array2, "数组对象必须一致");
        Assertions.assertEquals(list1, list2, "list对象必须一致");

        //3. 验证复杂数组和集合对象
        Assertions.assertEquals(list1.size(), list2.size(), "list对象长度不一致");
        for (int i = 0; i < list1.size(); i++) {
            Assertions.assertEquals(list1.get(i).getName(), list2.get(i).getName(), String.format("用户(%s)名称不一致", i));
            Assertions.assertEquals(list1.get(i).getSysUserCode(), list2.get(i).getSysUserCode(), String.format("用户(%s)代码不一致", i));
        }

        //4. 通过序列化验证数据对象
        Assertions.assertEquals(list1.size(), list2.size(), "list对象长度不一致");
        for (int i = 0; i < list1.size(); i++) {
            Assertions.assertEquals(list1.get(i).getName(), list2.get(i).getName(), String.format("用户(%s)名称不一致", i));
            Assertions.assertEquals(list1.get(i).getSysUserCode(), list2.get(i).getSysUserCode(), String.format("用户(%s)代码不一致", i));
        }

        //5.通过序列化验证数据对象
        Assertions.assertEquals(text, JSON.toJSONString(list1), "用户列表不一致");

        //⑤验证异常对象内容
        Exception aThrows = Assertions.assertThrows(Exception.class, () -> userServiceImpl.throwException(), "异常类型不一致");
        Assertions.assertEquals("aaa", aThrows.getMessage(), "异常类型不一致");

    }
    // ⑧验证依赖对象
    @Test
    void testValidationDependencyObject() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 1. 验证模拟对象没有任何方法调用
        Mockito.verifyNoInteractions(userMapper);

        // 2. 验证模拟对象没有更多方法调用，除了已经验证的部分
        Mockito.verifyNoMoreInteractions(userMapper);

        // 3. 清除模拟对象所有方法调用标记
        // 3.1 清除所有对象调用
        Mockito.clearInvocations();
        // 3.2 清除指定对象调用
        Mockito.clearInvocations(userMapper);
        userMapper.handlerList(Arrays.asList(new User()));
        ArgumentCaptor<List<User>> userCreateListCaptor = ArgumentCaptor.forClass(List.class);
        Mockito.verify(userMapper).handlerList(userCreateListCaptor.capture());
        List<User> value = userCreateListCaptor.getValue();
        System.out.println(value);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme