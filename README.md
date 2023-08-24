## 1 maven集成

> 默认情况下，maven找不到junit5的单元测试用例

执行 mvn clean test 发现

![图片](https://raw.githubusercontent.com/Levio-z/MyPicture/img/img/1.png)

### 步骤一：配置maven去找到单元测试

maven Surefire Plugin version 2.22.0 甚至更高

```java
<plugin>
   <groupId>org.apache.maven.plugins</groupId>
   <artifactId>maven-surefire-plugin</artifactId>
   <version>3.0.0-M5</version>
 </plugin>
```

### 步骤二：运行单元测试

```java
mvn clean test
```

### 步骤三：生成单元测试报告（略）

### 忽略测试失败

```

<plugin>
   <groupId>org.apache.maven.plugins</groupId>
   <artifactId>maven-surefire-plugin</artifactId>
   <version>3.0.0-M5</version>
   <configuration>
       <testFailureIgnore>true</testFailureIgnore>
   </configuration>
 </plugin>
```

## 2 生成代码覆盖率

Jacoco 提供了一个maven插件生成覆盖率报告

Jacoco 含义：java code coverge 含义java代码覆盖率

```java
<plugin>
                <groupId>org.jacoco</groupId>
                <artifactId>jacoco-maven-plugin</artifactId>
                <version>0.8.10</version>
                <executions>
                    <execution>
                        <id>jacoco-prepare</id>
                        <goals>
                            <goal>prepare-agent</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>jacoco-reort</id>
                        <phase>test</phase>
                        <goals>
                            <goal>report</goal>
                        </goals>
                    </execution>
                </executions>
</plugin>
```

![图片](https://raw.githubusercontent.com/Levio-z/MyPicture/img/img/2.png)

![图片](https://raw.githubusercontent.com/Levio-z/MyPicture/img/img/3.png)

- **Instructions (指令)**：代码中的所有可执行指令的覆盖率。这包括方法调用、条件分支、循环等。
- **Instruction Coverage (指令覆盖率)**：指令覆盖率是指**已经执行的指令占总指令数的比例。**
- **Branches (分支)**：代码中所有条件语句的覆盖率。这个指标表示在这些条件语句中，每个分支是否都被执行了至少一次。
- **Branch Coverage (分支覆盖率)**：分支覆盖率是指**已经执行的分支占总分支数的比例。**
- **Complexity (复杂度)**：代码中的复杂性覆盖率。**这个指标表示是否覆盖了所有可能的代码路径，以确保代码的正确性。**
- **Lines (行)**：代码中的每一行的覆盖率。这个指标表示每一行是否都至少执行了一次。
- **Methods (方法)**：代码中的方法的覆盖率。这个指标表示每个方法是否都至少执行了一次。

# 01 走进单元测试

什么是单元测试？

针对程序模块（软件设计的最小单元）来进行正确性校验的工作。

单元测试和集成测试。

人工测试费时费力，自动化测试。

## 1.1 单元测试特征

快速：应该花很少的时间来进行单元测试

独立：独立，可以独立运行

可重复：运行单元测试的结果是一致的

自检查：测试应该不需要人工介入，自动检测测试是否通过

## 1.2 单元测试、集成测试、系统级别测试

![图片](https://raw.githubusercontent.com/Levio-z/MyPicture/img/img/4.png)

![图片](https://raw.githubusercontent.com/Levio-z/MyPicture/img/img/5.png)

测试应该是为了你最担心的地方去测试，而不是为了测试而测试。

**做一件事应该关注他的价值，而不是事情本身，应该把它当成一个工具。**

# 02 为什么要单元测试

对之前遗留的代码不太有信心修改，不愿意复用别人的一个东西，或者去重构。

原因也很明确，**无法确认修改后是否对原有代码造成破坏。**

我们也害怕别人改我们的代码。

## 时间成本指标

![图片](https://raw.githubusercontent.com/Levio-z/MyPicture/img/img/6.png)

微软的统计数据：bug在单元测试阶段被发现，平均耗时3.25小时，如果漏到系统测试阶段，要花费11.5小时。

**测试中出现bug的阶段**

![图片](https://raw.githubusercontent.com/Levio-z/MyPicture/img/img/7.png)

85%的缺陷都在代码设计阶段产生，而发现bug的阶段越靠后，耗费成本就越高，指数级别的增高。所以，在早期的单元测试就能发现bug，省时省力，一劳永逸，何乐而不为呢

## 具体案例

> 来源单元测试的艺术

找了开发能力相近的两个团队，同时开发相近的需求。进行单测的团队在编码阶段时长增长了一倍，从7天到14天，但是，这个团队在集成测试阶段的表现非常顺畅，bug量小，定位bug迅速等。最终的效果，整体交付时间和缺陷数，均是单测团队最少。

![图片](https://github.com/Levio-z/MyPicture/blob/img/img/8.png)

⇒不进行单测，交付时间长，产出的代码缺陷更多，修复缺陷花费的时间和成本指数上升

## 重要性

### 对团队的价值

- 缩短反馈周期：降低了修复缺陷的成本，为企业为团队降低了成本，提高了产出
  - 单元测试是所有测试中最底层的一类测试，是第一个环节，也是最重要的一个环节，是唯一一次有保证能够代码覆盖率达到100%的测试，是整个软件测试过程的基础和前提，单元测试防止了开发的后期因bug过多而失控，单元测试的**性价比**是最好的。
- 保证质量情况下提高交付速度

### 对个人的价值

- 驱动设计：明确代码功能模块职责，帮助系统的设计灵活、松耦合
- 活文档：可执行且永远最新的文档
  - 测试方法名可以看出我们的意图
- 安全重构：后续重构更安全更可靠
  - 持续进行单元测试来发现代码有没有被破坏
- 易于调试：帮助开发者快速定位bug，
  - **线上发现问题，快速调试**
- 提升信心：能够在开发中提供快速反馈
- 放心重构
- 代码规范、优化，可测试性的代码，便于后期维护和扩展

# 03 如何做好单元测试

![图片](https://github.com/Levio-z/MyPicture/blob/img/img/9.png)

## 四步骤

### 步骤一：定义对象阶段

①定义被测对象

②模拟依赖对象（类成员）

③注入依赖对象（类成员）

### 步骤二：模拟方法阶段

②模拟依赖对象（参数或返回值）

④模拟依赖方法

### 步骤三：调用方法阶段

②模拟依赖对象（测试方法入参）

⑤调用测试方法

⑦验证数据对象（返回值）

### 步骤四：验证方法阶段

⑥验证依赖方法

⑦验证数据对象（参数）

⑧验证依赖对象

## 八方法

### ①定义被测对象

> 编写测试用例前，需要首先初始化被测对象，直接初始化或通过Spy等实例化

```java
// 1.直接构建对象
User user = new User();

// 2.利用spy
UserServiceImpl spy = spy(UserServiceImpl.class);

// 3.利用@spy注解
private UserServiceImpl spy = new UserServiceImpl();

// 4.使用@InjectMocks注解
@InjectMocks
UserServiceImpl userServiceImpl;
```

使用建议

- 推荐使用@InjectMocks。

`@InjectMocks`

@InjectMocks创建的Mock对象可以直接调用真实的代码，**其他用@Mock创建的对象将被注到其中**正因为这个特性，@InjectMock修饰被测试类，@Mock修饰其依赖。

`@Spy`

对于被@Spy修饰的成员变量调用时，**默认是进行真实调用**

> 注意事项：
>
> 对于 Spy 对象，应该使用 `doReturn()` 等方法进行存根，而不是 `when()` 方法。
>
> ```java
> Mockito.doReturn("some value").when(spyObject).getSomeMethod();
> ```

### ②模拟依赖对象

> 编写测试用例时，模拟各种依赖对象，类成员，模拟方法出参和入参，测试方法入参

```java

// 1.直接构建对象
User user = User.builder().name("test").build();

// 2.反序列化对象
String text = "{\"name\":\"测试\"}";
User user = JSON.parseObject(text , User.class);
String listText = "[{\"name\":\"测试\"}]";
List<User> userArray = JSON.parseArray(listText, User.class);
String mapText= "{\"test\":{\"name\":\"测试\"}}";
Map<String, User> stringUserMap = JSON.parseObject(JSON.toJSONString(map), new TypeReference<Map<String, User>>() {
        });

// 3.利用Mocktio.mock 方法
SyncService mock = Mockito.mock(SyncService.class);
List<String> mockList = Mockito.mock(List.class);

// 4.利用@Mock注解
@Mock
SyncService syncService;
// 3.利用Mocktio.spy 方法
UserServiceImpl spy = Mockito.spy(UserServiceImpl.class);
UserServiceImpl spy = Mockito.spy(new UserServiceImpl());

// 4.利用@spy注解
@Spy
UserServiceImpl spy = new UserServiceImpl();

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
}
```

使用建议：

- 推荐序列化：避免大幅度set
- 只用到一个对象几个方法，可以mock一个对象，再mock对象的一些方法

### ③注入依赖对象

```java
// 1.使用@InjectMocks注入
@Mock
SyncService syncService;
@InjectMocks
UserServiceImpl userServiceImpl;

// 2.ReflectionTestUtils
//在MockitoAnnotations.openMocks(this)后使用
ReflectionTestUtils.setField(realnameDealerServiceImpl, "baseMapper", realnameDealerMapper);

// 3.利用set方法注入

```

建议：

大部分使用@InjectMocks足够，偶尔使用

ReflectionTestUtils.setField(realnameDealerServiceImpl, "baseMapper", realnameDealerMapper);

### ④模拟依赖方法

4.1 根据返回模拟方法

```java
// 1.模拟无返回值方法
Mockito.doNothing().when(userMapper).delete(buildUser());

// 2.模拟单个返回值方法
Mockito.when(userMapper.save(any())).thenReturn("saveResponse");
Mockito.doReturn("saveResponse").when(userMapper).save(any());

// 3.模拟调用方法多次，返回值不同
Mockito.doReturn("saveResponse","saveResponse1").when(userMapper).save(any());
Mockito.when(userMapper.save(any())).thenReturn("saveResponse","saveResponse1");

// 4.模拟方法定制返回值
Map<String, User> map = new HashMap<>();
map.put("UC001", User.builder().sysUserCode("UC001").name("1号用户").build());
map.put("UC002", User.builder().sysUserCode("UC002").name("2号用户").build());
Answer<User> aswser = (invocation) -> {
            Object[] args = invocation.getArguments();
            User o = map.get(args[0].toString());
            return o;
        };
Mockito.when(userMapper.getUserByCode(anyString())).thenAnswer(aswser);

// 另外的模拟方式
Mockito.doAnswer(aswser).when(userMapper).getUserByCode(anyString());

// 5.模拟方法抛出异常
Exception e = new Exception("e");
Mockito.doThrow(e).when(userMapper).getUserByCode(anyString());
Mockito.when(userMapper.getUserByCode(anyString())).thenThrow(e);
// 异常也可以是类
Mockito.when(userMapper.getUserByCode(anyString())).thenThrow(Exception.class);
Mockito.doThrow(Exception.class).when(userMapper).getUserByCode(anyString());

// 6.模拟方法抛出多个异常
Mockito.doThrow(Exception.class,Exception.class,Exception.class).when(userMapper).getUserByCode(anyString());
Mockito.doThrow(e,e).when(userMapper).getUserByCode(anyString());

// 7.直接调用真实方法
Mockito.doCallRealMethod().when(userMapper).getUserByCode(anyString());
Mockito.when(userMapper.getUserByCode(anyString())).thenCallRealMethod();
```

4.2 根据参数模拟方法

```java

// 1.模拟无参数方法
Mockito.doReturn(new ArrayList<>()).when(userMapper).selectAll();
Mockito.when(userMapper.selectAll()).thenReturn(new ArrayList<>());

// 2.模拟指定参数方法
Mockito.doReturn(buildUser()).when(userMapper).getUserByCode(userCode);
Mockito.when(userMapper.getUserByCode(userCode)).thenReturn(buildUser());

// 3.模拟任意参数方法
Mockito.doReturn(buildUser()).when(userMapper).getUserByCode(anyString());
Mockito.when(userMapper.getUserByCode(anyString())).thenReturn(buildUser());

// 4.模拟可空参数方法
Mockito.doReturn(buildUser()).when(userMapper).getUserByCode(anyString(),Mockito.nullable(String.class));
Mockito.when(userMapper.getUserByCode(anyString(),Mockito.nullable(String.class))).thenReturn(buildUser());

// 5.模拟必空参数方法
Mockito.doReturn(buildUser()).when(userMapper).getUserByCode(anyString(),Mockito.isNull());
Mockito.when(userMapper.getUserByCode(anyString(),Mockito.isNull())).thenReturn(buildUser());

// 6.模拟不同参数方法
Mockito.doReturn(buildUser()).when(userMapper).getUserByCode("000");
Mockito.doReturn(buildUser1()).when(userMapper).getUserByCode("001");

// 7.模拟可变参数方法
// 匹配一个
Mockito.doReturn("1").when(userMapper).delete(Mockito.any());
// 匹配多个
Mockito.doReturn("2").when(userMapper).delete(Mockito.<User>any());
// 指定参数
Mockito.doReturn("1").when(userMapper).delete(buildUser(),buildUser1());
```

4.3 模拟特殊方法

```java
// 1.模拟静态方法(有返回值)
MockedStatic finalServiceMockedStatic = mockStatic(FinalServiceImpl.class);
Mockito.when(FinalServiceImpl.check(any())).thenReturn("s");
// 调用
String check = finalService.check(new User());
String check1 = FinalServiceImpl.check(any());
finalServiceMockedStatic.close();
```

junit4 PowerMocktio 模拟静态方法和私有方法junit5不支持

### ⑤调用测试方法

```java
// 1.直接调用
User result = userServiceImpl.getUserByCode("userCode");

// 2.私有方法
// 获取私有方法
Method privateMethod = FinalServiceImpl.class.getDeclaredMethod("getUserByCode", String.class);
privateMethod.setAccessible(true); // 设置私有方法可访问
// 调用私有方法
User result = (User) privateMethod.invoke(finalService, "000");
```

### ⑥验证依赖方法

6.1 根据参数验证依赖方法调用

```java
User user = new User();
 // 1.验证无参方法调用
 Mockito.verify(userMapper).selectAll();

 // 2.验证指定参数方法调用
 Mockito.verify(userMapper).save(user);

 // 3.验证任意参数方法调用
 Mockito.verify(userMapper).save(any());

 // 4.验证可空参数方法调用
 Mockito.verify(userMapper).getUserByCode(anyString(),Mockito.nullable(String.class));

 // 5.验证必空参数方法调用
 Mockito.verify(userMapper).getUserByCode(anyString(),Mockito.isNull());

 // 6.验证可变参数方法调用
 Mockito.verify(userMapper).delete(Mockito.any());
 //匹配一个
 Mockito.verify(userMapper).delete(Mockito.<User>any());
 //匹配多个
 Mockito.verify(userMapper).delete(buildUser(),buildUser1());
```

6.2 验证方法调用次数

```java
// 1.验证方法默认调用1次
Mockito.verify(userMapper).delete(Mockito.any());

// 2.验证方法从不调用
Mockito.verify(userMapper,Mockito.never()).delete(Mockito.any());

// 3.验证方法调用n次
Mockito.verify(userMapper,Mockito.times(2)).delete(Mockito.any());

// 4.验证方法调用至少1次
Mockito.verify(userMapper,Mockito.atLeastOnce()).delete(Mockito.any());

// 5.验证方法调用至少n次
Mockito.verify(userMapper,Mockito.atLeast(3)).delete(Mockito.any());

// 6.验证方法调用最多1次
Mockito.verify(userMapper,Mockito.atMostOnce()).delete(Mockito.any());

// 7.验证方法调用最多n次
Mockito.verify(userMapper,Mockito.atMost(3)).delete(Mockito.any());

// 8.验证方法调用1次
Mockito.verify(userMapper,Mockito.only()).delete(Mockito.any());

// 9.验证方法调用1次,calls只配合用于验证模拟对象的方法调用顺序
InOrder inOrder = inOrder(userMapper);
inOrder.verify(userMapper,Mockito.calls(1)).delete(Mockito.any());
```

6.3 验证方法调用并捕获参数

```java
// 1. 使用ArgumentCaptor.forClass方法定义捕获器,捕获多次
Mockito.doReturn("String").when(userMapper).delete(buildUser());
userServiceImpl.delete(buildUser());
userServiceImpl.delete(buildUser());
ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
Mockito.verify(userMapper,times(2)).delete(userArgumentCaptor.capture());
List<User> allValues = userArgumentCaptor.getAllValues();
System.out.println(allValues);
// 2. 使用ArgumentCaptor.forClass方法定义捕获器,捕获1次
Mockito.doReturn("String").when(userMapper).save(buildUser());
userServiceImpl.save(buildUser());
ArgumentCaptor<User> userArgumentCaptor2 = ArgumentCaptor.forClass(User.class);
Mockito.verify(userMapper,times(1)).save(userArgumentCaptor2.capture());
User value = userArgumentCaptor2.getValue();
System.out.println(value);
// 3. 使用@Captor注解定义捕获器
@Captor
private ArgumentCaptor<User> userArgumentCaptor;
```

### ⑦验证数据对象

```java
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
Assertions.assertSame(user,user2, "用户必须一致");

//2. 验证数据对象不一致
Assertions.assertNotSame(user,user2, "用户必须不一致");

//④验证数据对象值
      
//1. 验证简单数据对象
Assertions.assertNotEquals(user,user2, "用户必须不相等");
Assertions.assertEquals(user,user2, "用户必须相等");

//2. 验证简单数组和集合对象
Assertions.assertArrayEquals(array,array2,"数组对象必须一致");
Assertions.assertEquals(list1,list2, "list对象必须一致");

//3. 验证复杂数组和集合对象
Assertions.assertEquals(list1.size(),list2.size(), "list对象长度不一致");
for (int i =0;i<list1.size();i++){
    Assertions.assertEquals(list1.get(i).getName(),list2.get(i).getName(), String.format("用户(%s)名称不一致",i));
    Assertions.assertEquals(list1.get(i).getSysUserCode(),list2.get(i).getSysUserCode(), String.format("用户(%s)代码不一致",i));
}

//4. 通过序列化验证数据对象
Assertions.assertEquals(list1.size(),list2.size(), "list对象长度不一致");
for (int i =0;i<list1.size();i++){
    Assertions.assertEquals(list1.get(i).getName(),list2.get(i).getName(), String.format("用户(%s)名称不一致",i));
    Assertions.assertEquals(list1.get(i).getSysUserCode(),list2.get(i).getSysUserCode(), String.format("用户(%s)代码不一致",i));
}

//5.通过序列化验证数据对象
Assertions.assertEquals(text,JSON.toJSONString(list1),"用户列表不一致");

//⑤验证异常对象内容
 Exception aThrows = Assertions.assertThrows(Exception.class, () -> userServiceImpl.throwException(), "异常类型不一致");
 Assertions.assertEquals("aaa", aThrows.getMessage(), "异常类型不一致");
```

### ⑧验证依赖对象

```java
// 1. 验证模拟对象没有任何方法调用
Mockito.verifyNoInteractions(userMapper);

// 2. 验证模拟对象没有更多方法调用，除了已经验证的部分
Mockito.verifyNoMoreInteractions(userMapper);

// 3. 清除模拟对象所有方法调用标记
// 3.1 清除所有对象调用
Mockito.clearInvocations();
// 3.2 清除指定对象调用
Mockito.clearInvocations(userMapper);
```

## 命名规范

### ①测试类命名

类名+Test

`UserServiceImplTest`

### ②**测试方法命名**

test开头并以被测试方法结尾。

testBatchCreate(批量创建)

添加”With+条件“来表达不同的测试用例

**1.按照结果命名：**

- testBatchCreateWithSuccess(测试：批量创建-成功)；
- testBatchCreateWithFailure(测试：批量创建-失败)；
- testBatchCreateWithException(测试：批量创建-异常)；

**2.按照参数命名：**

- testBatchCreateWithListNull(测试：批量创建-列表为NULL)；
- testBatchCreateWithListEmpty(测试：批量创建-列表为空)；
- testBatchCreateWithListNotEmpty(测试：批量创建-列表不为空)；

**3.按照意图命名：**

- testBatchCreateWithNormal(测试：批量创建-正常)；
- testBatchCreateWithGray(测试：批量创建-灰度)；
- testBatchCreateWithException(测试：批量创建-异常)；

### ③**测试类资源目录命名**

类名小写开头+方法名

`userServiceImplTest/testBatchCreateWithSuccess`

### ④**测试资源文件命名**

在被测试代码中，所有参数、变量都已经有了命名。所以，建议优先使用这些参数和变量的名称，并加后缀“.json”标识文件格式

反序列化为对应的数据对象，这些数据对象的**变量名称**也应该跟资源文件名称保持一致。

```java
String text = ResourceHelper.getResourceAsString(getClass(), path + "userCreateList.json");
List<UserCreateVO> userCreateList = JSON.parseArray(text, UserCreateVO.class);
userService.batchCreate(userCreateList);
```

### ⑤测试资源文件存储

在测试资源目录和名称定义好之后，就需要存入测试资源文件了。存储方式总结如下：

1. 如果是测试类下所有测试用例共用的资源文件，建议存储在测试类资源目录下，比如：testUserService；
2. 如果是测试用例独有的资源文件，建议存储在测试方法资源目录下，比如：testUserService/testBatchCreateWithSuccess；
3. 如果是某一被测方法所有的测试用例共用的资源文件，建议存储在不带任何修饰的测试方法资源目录下，比如：testUserService/testBatchCreate
