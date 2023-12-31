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

人工测试费时费力，测试要自动化。

> 起止测试需要自动化，除了工作中的代码设计阶段结束后，编码阶段都应该自动化。生活中能自动化的都应该自动化，你就能将时间花在更有价值的一些事情上，能够提高生产力。每个人不同，喜欢技术可以深挖源码，参与开源，多陪陪家人。

## 1.1 单元测试特征

快速：应该花很少的时间来进行单元测试

> 单测不像集成测试那样，你测不了几次，单测你使用它的次数很多。砸门需要降低你运行单测的启动成本，你能够迅速得到反馈。

独立：独立，可以独立运行

> 解耦。如果不是独立的有什么坏处，修改单测变得困难，我只需要修改case1的功能，却需要将一个方法的所有case都看一遍。没必要啊。独立就是，我只需要修改case1，那就修改case1的代码，跑case1。

可重复：运行单元测试的结果是一致的

> 不可能一个人维护一个程序从开始到从始至终。最好的单测我的理解是什么，单测所有case覆盖所有的条件。只要需求没有改变，你可以随时重构，提高代码质量，甚至不需要去看单测，去维护单测。有些与当前时间依赖度比较高的，你传入的时间参数必须大于现在的时间。2023.9.30，到了，30号后。我走了，后面同志就必须要看懂这块的单测，才能使用。大大降低了可维护性和效率吗。

自检查：测试应该不需要人工介入，自动检测测试是否通过

> 自动化，很多单测框架都提供了断言以及验证方法的工具，那为什么还要人工去做这些事情呢？机器能做，就让机器去做好了呗。假设这块需要人工做，我就又要花时间去理解单测的代码。这不纯纯降低生产力吗？

## 1.2 单元测试、集成测试、系统级别测试

![图片](https://raw.githubusercontent.com/Levio-z/MyPicture/img/img/4.png)

![图片](https://raw.githubusercontent.com/Levio-z/MyPicture/img/img/5.png)

测试应该是为了你最担心的地方去测试，而不是为了测试而测试。

**做一件事应该关注他的价值，而不是事情本身，应该把它当成一个工具。**

# 02 为什么要单元测试

对之前遗留的代码不太有信心修改，不愿意复用别人的一个东西，或者去重构。

原因也很明确，**无法确认修改后是否对原有代码造成破坏。**

我们也害怕别人改我们的代码。

> 没有单测，无论是对代码质量还是协作都是有害的。
>
> 没有单测其实也是造成屎山的原因之一，原有代码不适合现有需求，但是因为无法确认修改后是否对原有代码造成破坏。如果单测写的比较好的情况下，你就能确认修改后是否对原有代码造成破坏。

## 时间成本指标

![图片](https://raw.githubusercontent.com/Levio-z/MyPicture/img/img/6.png)

微软的统计数据：bug在单元测试阶段被发现，平均耗时3.25小时，如果漏到系统测试阶段，要花费11.5小时。

**测试中出现bug的阶段**

![图片](https://raw.githubusercontent.com/Levio-z/MyPicture/img/img/7.png)

85%的缺陷都在代码设计阶段产生，而发现bug的阶段越靠后，耗费成本就越高，指数级别的增高。所以，在早期的单元测试就能发现bug，省时省力，一劳永逸，何乐而不为呢

## 具体案例

> 来源单元测试的艺术

找了开发能力相近的两个团队，同时开发相近的需求。进行单测的团队在编码阶段时长增长了一倍，从7天到14天，但是，这个团队在集成测试阶段的表现非常顺畅，bug量小，定位bug迅速等。最终的效果，整体交付时间和缺陷数，均是单测团队最少。

![图片](https://raw.githubusercontent.com/Levio-z/MyPicture/img/img/8.png)

解读：单测是花时间去coding的，编码时间更长。大幅度提高代码质量，加快bug定位，发现bug快，修bug也快。

总的来说：不进行单测，交付时间长，产出的代码缺陷更多，修复缺陷花费的时间和成本指数上升

## 重要性

### 对团队的价值

- 缩短验证程序正确性的反馈周期：降低了修复缺陷的成本，为企业为团队降低了成本，提高了产出，达到了降本增效
  - 单元测试是所有测试中最底层的一类测试，是第一个环节，也是最重要的一个环节，是唯一一次有保证能够代码覆盖率达到100%的测试，是整个软件测试过程的基础和前提，单元测试防止了开发的后期因bug过多而失控，单元测试的**性价比**是最好的。
- 保证质量情况下提高交付速度

### 对个人的价值

- 驱动设计：明确代码功能模块职责，帮助系统的设计灵活、松耦合。

  > case写的好的情况下，使这段代码的逻辑更清晰，你不会写上一些没用的逻辑，也不会遗漏一些分支。

- 活文档：可执行且永远最新的文档
  - 测试方法名可以看出我们的意图
  
  > 比接口文档和详细设计更好，对于开发人员来说，明确的看到这个方法的每种case都实际做了什么。对于开发人员来说就是最好的接口文档。
  
- 安全重构：后续重构更安全更可靠
  - 持续进行单元测试来发现代码有没有被破坏
  
- 易于调试：帮助开发者快速定位bug，
  - **线上发现问题，快速调试**
  
- 提升信心：能够在开发中提供快速反馈

  > 你写好了所有的case，就可以一步一步实现了。像一个todo一样，明确自己接下来要干什么。

- 可测试性的代码，便于后期维护和扩展。

### 关于为什么要写单测各种维度的讨论和理解

- **让人在修改代码之后能感到安心，踏实**：工作了一些年头，我觉得单元测试起到的最重要作用其实是：（单元测试跑过之后能比用飘柔更自信）。只要跑一把单元测试，就能自动化验证程序逻辑的正确性，而无需在提交代码之前提心吊胆、担心会漏掉什么情况没有处理或者自己新加入的逻辑制造了bug。
- **面向重构**：[单元测试](https://www.zhihu.com/search?q=单元测试&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"answer"%2C"sourceId"%3A120164282})，或者更大一些的[自动化测试](https://www.zhihu.com/search?q=自动化测试&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"answer"%2C"sourceId"%3A120164282})，对提高软件质量是有很大帮助的。通过一系列预先设计的规则，就可以覆盖大量的测试点。尤其是对重构一类的任务，确保修改前后系统行为不变很重要，而修改后的[回归测试](https://www.zhihu.com/search?q=回归测试&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"answer"%2C"sourceId"%3A120164282})工作量又极其繁重，此时单元测试，或者自动化测试就能体现出无以伦比的效率。
- **核心理解**：**验证“自己写的一小段代码是不是符合设计逻辑的“。而不是调用的其他函数。**
- **成本**：有效的开发阶段测试会占用掉开发者30%左右的精力
- **学习难度**：**特别强调下，搭建有效的自动化测试的工作量并不小**。但实际上为了让TestCase发挥作用，让测试容易运行，可以隔离，可重复，稳定的跑，需要学习和实施的东西非常多，甚至不亚于功能开发本身。对测试工作量的低估最终会导致让这个工作不了了之，变成面子工程。如果你是一个技术lead，想带领团队写测试，但以为“就是很小的工作量，随便写一下就会有效果”，那么必然会吃瘪。

## Mocktio 框架简介

### Mocktio概述

**什么是Mockito？**

Mockito是一个**功能强大**的Java测试框架，通过Mockito可以**创建Mock对象**，**排除外部依赖**对被测试类的干扰。通俗地讲就是我们在对某个类测试时，涉及到一些RPC调用，数据库，缓存的操作等，都可以将对应的依赖Mock掉（模拟），让其返回我们自己造的结果。

官网：https://site.mockito.org/

**为什么要用Mockit？**

Mockito能让代码对**外部系统隔离**，不需要进行各种初始化操作。仅仅从**单机的角度**看逻辑是否跑通。

**什么是Mock对象？**

维基百科对模拟对象的解释：

> 在面向对象程序设计中，模拟对象（英语：mock object，也译作**模仿对象**）是以可控的方式模拟真实对象行为的假的对象。**汽车设计者使用碰撞测试假人来模拟车辆碰撞中人的动态行为**

简单来说，就是用于模拟实际的对象（这些对象可能是对外部系统的依赖），从而**校验被测试类的方法调用是否符合预期。**

**什么是打桩？**

打桩也叫stub，存根。就是把所需的

**测试数据塞进对象**

中，关注点在于入参和出参。在Mockito 中使用 when(…).thenReturn(…) 描述了对象以某个入参调用某个方法时，返回thenReturn中指定的出参。这个过程就叫

**Stub打桩**

。只要这个方法被 stub了，每次调用，就会一直返回这个stub的值。

Mockito的**使用过程**很简单：

1. 将被测试接口相关的外部依赖对象转换为Mock对象，然后打桩。
2. 执行测试接口代码。
3. 校验返回的关键结果是否正确

下文就详细解释这三个步骤。

**mocktio的原理**

在mock方法中，mockito会利用**ByteBuddy框架生成对应的字节码文件**，然后利用**classLoader加载**。其实这个过程就是**动态代理生成的过程**，只不过这一步你也可以用cglib实现。

对其某个方法的调用都会触发handler，比如`Mockito.when(orderService.getOrder(1L)`时，可以理解成维护一个Map，key就是invocation对象，里面记录了方法名，入参等。总之可以用它进行反射调用。value则为`thenReturn(order`中的对象。

# 03 如何做好单元测试

![图片](https://raw.githubusercontent.com/Levio-z/MyPicture/img/img/9.png)

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
@Spy
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
   参考链接：https://www.zhihu.com/search?type=content&q=%E8%80%8C%E6%88%91%E5%BF%AB%E9%80%9F%E7%BC%96%E5%86%99Java%E5%8D%95%E5%85%83%E6%B5%8B%E8%AF%95%E7%94%A8%E4%BE%8B%E7%9A%84%E6%8A%80%E5%B7%A7%E5%B0%B1%E6%98%AF%E2%80%94%E2%80%94JSON%E5%BA%8F%E5%88%97%E5%8C%96%E3%80%82

资源辅助工具类

```java
package com.lion.utcompanytraining.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 资源辅助类
 */
public final class ResourceHelper {

    /**
     * 构造方法
     */
    private ResourceHelper() {
        throw new UnsupportedOperationException();
    }

    /**
     * 以字符串方式获取资源
     *
     * @param clazz 类
     * @param name 资源名称
     * @return 字符串
     */
    public static <T> String getResourceAsString(Class<T> clazz, String name) {
        try (InputStream is = clazz.getResourceAsStream(name)) {
            return IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (IOException  e) {
            throw new RuntimeException(String.format("以字符串方式获取资源(%s)异常", name), e);
        }
    }

    /**
     * 以字符串方式获取对象
     *
     * @param clazz 类
     * @param name 资源名称
     * @return 字符串
     */
    public static <T,Z> Z getResourceAsObject(Class<T> clazz, String name,Class<Z> clazz1) {
        String string = getResourceAsString(clazz, name);
        return JSON.parseObject(string, clazz1);
    }

    /**
     * 反序列化List对象
     *
     * @param clazz 类
     * @param name 资源名称
     * @return 字符串
     */
    public static <T,Z> List<Z> getResourceAsList(Class<T> clazz, String name, Class<Z> clazz1) {
        String string = getResourceAsString(clazz, name);
        return JSON.parseArray(string, clazz1);
    }

    /**
     * 反序列化映射对象
     *
     * @param clazz 类
     * @param name 资源名称
     * @return 字符串
     */
    public static <T,K,Y> Map<K,Y> getResourceAsMap(Class<T> clazz, String name, Class<K> clazz1, Class<Y> clazz2) {
        String string = getResourceAsString(clazz, name);
        return JSON.parseObject(string, new TypeReference<Map<K, Y>>() {});
    }
}
```

# 测试驱动开发

![Untitled](https://raw.githubusercontent.com/Levio-z/MyPicture/img/img/202308291626321.png)

- 列清楚了要开发和测试的任务清单
- 识别清楚边缘情况
- 以最小增量开发代码
- 通过测试可以增加开发的信心
- 给了你重构的自由，测试是你的安全网

## 步骤

①　快速新增一个测试

②　运行所有的测试（有时候只需要运行一个或一部分），发现新增的测试不能通过

③　做一些小小的改动，尽快地让测试程序可运行，为此可以在程序中使用一些不合情理的方法

④　运行所有的测试，并且全部通过

⑤　重构代码，以消除重复设计，优化设计结构

## 实践

### 编写一段示例代码

打印1-100

被3整除，打印Fizz

被5整除，打印Buzz

被3整除也被5整除，打印FizzBuzz

都不满足，打印原来的数字

### ①先写一个失败的测试

```jsx
@Test
    @DisplayName("FizzBuzz")
    void testFizzBuzz(){
        String expected ="Fizz";
        assertEquals(expected,FizzBuzzUtil.compulete(3),"Should return Fizz");
    }
```

写好方法名，甚至可以右键在方法内创建方法

### ②完成方法的一部分，使他满足Fizz

```jsx
//被3整除，打印Fizz
    @Test
    @Order(1)
    @DisplayName("Divisible By Three")
    void testForDivisibleByThree(){
        String expected ="Fizz";
        assertEquals(expected,FizzBuzzUtil.compute(3),"Should return Fizz");
    }
```

### ③pass，编写下一个测试，直到所有分支都满足，代码逻辑都实现

```jsx
package com.lion.ut.utils;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FizzBuzzUtilTest {
    /**
     * 打印1-100
     *
     * 被3整除，打印Fizz
     *
     * 被5整除，打印Buzz
     *
     * 被3整除也被5整除，打印FizzBuzz
     *
     * 都不满足，打印原来的数字
     */
    //被3整除，打印Fizz
    @Test
    @Order(1)
    @DisplayName("Divisible By Three")
    void testForDivisibleByThree(){
        String expected ="Fizz";
        assertEquals(expected,FizzBuzzUtil.compute(3),"Should return Fizz");
    }
    //被5整除，打印Buzz
    @Test
    @Order(2)
    @DisplayName("Divisible By Five")
    void testForDivisibleByFive(){
        String expected ="Buzz";
        assertEquals(expected,FizzBuzzUtil.compute(5),"Should return Buzz");
    }

    //被3和5整除，打印Buzz
    @Test
    @Order(3)
    @DisplayName("Divisible By Three And Five")
    void testForDivisibleByThreeAndFive(){
        String expected ="FizzBuzz";
        assertEquals(expected,FizzBuzzUtil.compute(15),"Should return FizzBuzz");
    }

    //都不满足，打印原来的数字
    @Test
    @Order(4)
    @DisplayName("Divisible By Three And Five")
    void testElse(){
        String expected ="1";
        assertEquals(expected,FizzBuzzUtil.compute(1),"Should return source");
    }
}
```

方法：

```java
public static String compute(Integer a){
    boolean b = a % 3 == 0;
    boolean b1 = a % 5 == 0;
    if (b&&b1){
        return "FizzBuzz";
    }else if (b) {
        return "Fizz";
    } else if (b1) {
        return "Buzz";
    } else {
        return Integer.toString(a);
    }
}
```

### ④重构，运行所有测试

```java
public static String compute(Integer i){
        StringBuilder stringBuilder = new StringBuilder();

        if ( i % 3 == 0){
            stringBuilder.append("Fizz");
        }

        if ( i % 5 == 0){
            stringBuilder.append("Buzz");
        }

        if ("".equals(stringBuilder.toString())){
            stringBuilder.append(i);
        }

        return stringBuilder.toString();
    }
```

# 具体使用SOP以及查询



## 特殊使用case

1. 使用了Mybatis-plus

   ```java
   TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), SysConfig.class);
   ```

2. mock@value注解的变量

   ```java
   @Mock
   private Environment environment;
   when(environment.getProperty("offline.uploadDataHbaseTableName")).thenReturn("1,2");
   ```

3. mock静态方法

   ```java
   MockedStatic mockedStatic = mockStatic(HttpUtil.class);
   HttpRequest httpRequest = mock(HttpRequest.class);
   HttpResponse httpResponse = mock(HttpResponse.class);
   when(HttpUtil.createPost(anyString())).thenReturn(httpRequest);
   when(httpRequest.execute()).thenReturn(httpResponse);
   mockedStatic.close();
   ```

3. @Spy

   ```java
   //使用一下语法存根：
   doReturn("some value").when(spyObject).getSomeMethod();
   ```

4. 模拟私有变量

   ```java
   ReflectionTestUtils.setField(realnameDealerServiceImpl, "baseMapper", realnameDealerMapper);
   ```

5. 单元测试类中有ThreadLocal

6. 模拟对象存根却没有成功

   ```java
   所以mock的时候，mock的变量名也应该为remoteRestTemplate，将测试类中的restTemplate修改为remoteRestTemplate修复。
   ```

7. mock一个假对象及对象的方法(不需要全部使用对象的方法时)

   ```java
   // 创建一个返回类型为ResponseEntity的假对象
   ResponseEntity<String> mockResponse = Mockito.mock(ResponseEntity.class);
   // 设置假对象的返回值
   Mockito.when(mockResponse.getBody()).thenReturn("Hello, world!");
   ```

8.  SpringBoot模拟代理

   ```java
   mockStatic(AopContext.class);
   when(AopContext.currentProxy()).thenReturn(sysRoleServiceImpl);
   ```

9. mock上下文

   ```java
   //mock模拟上下文
   
   
   Authentication authentication =mock(Authentication.class);
   SecurityContext securityContext =mock(SecurityContext.class);
   SecurityContextHolder.setContext(securityContext);
   User applicationUser =mock(User.class);
   
   when(securityContext.getAuthentication()).thenReturn(authentication);
   when(authentication.getPrincipal()).thenReturn(applicationUser);
   
   when(applicationUser.getUsername()).thenReturn("applicationUser");
   ```
   
   

11. 对于链式调用

    ```java
        @Mock(answer = Answers.RETURNS_SELF)
        UserLoanDetail.UserLoanDetailBuilder userLoanDetailBuilder;
    
    
    UserLoanDetail userLoanDetail = mock(UserLoanDetail.class);
    Mockito.when(UserLoanDetail.builder()).thenReturn(userLoanDetailBuilder);
     when(userLoanDetailBuilder.build()).thenReturn(userLoanDetail);
    doReturn(true).when(userLoanDetail).insert();
    ```

12. SOP

    ```java
    @BeforeEach
        void setUp() {
            // 1.mybaitsPlusb
            TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), UserLoanDetail.class);
            MockitoAnnotations.openMocks(this);
            // 2.解决代码里直接this.baseMapper
            ReflectionTestUtils.setField(userLoanDetailServiceImpl, "baseMapper", baseMapper);
            // 3.mock模拟上下文
            LionUser applicationUser = mock(LionUser.class);
            Authentication authentication = mock(Authentication.class);
            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            SecurityContextHolder.setContext(securityContext);
            when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(applicationUser);
            when(applicationUser.getUsername()).thenReturn("applicationUser");
        }
    ```

    
