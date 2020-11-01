



# login-page

A login page based on Spring boot and MyBatis. The check code function is based on Kaptha and java.awt.

[TOC]

## 关键依赖项

- `spring-boot-starter-thymeleaf`
- `spring-boot-starter-web`
- `kaptcha`
- `mybatis-spring-boot-starter`
- `mysql-connector-java`

## 项目目录

```
📦src
 ┣ 📂main
 ┃ ┣ 📂java
 ┃ ┃ ┗ 📂cn
 ┃ ┃ ┃ ┗ 📂zxltech
 ┃ ┃ ┃ ┃ ┗ 📂login
 ┃ ┃ ┃ ┃ ┃ ┣ 📂bean
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Result.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜User.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂checkcode
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜GetImage.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜KaptchaConfig.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜KaptchaController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PageController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserMapper.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserService.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜LoginApplication.java
 ┃ ┗ 📂resources
 ┃ ┃ ┣ 📂static
 ┃ ┃ ┣ 📂templates
 ┃ ┃ ┃ ┣ 📜index.html
 ┃ ┃ ┃ ┣ 📜login.html
 ┃ ┃ ┃ ┗ 📜regist.html
 ┃ ┃ ┗ 📜application.yml
 ┗ 📂test
```

## 使用说明

1. 下载源代码；

2. 修改 `resources/application.yml` 中的 `Spring.database` 配置项，通常需要修改数据库名、用户名和密码；

3. 在数据库中建立与 `Bean/User.java` 相应的表，`sql` 脚本如下：

   - ```mysql
     CREATE TABLE `user` (
       `id` bigint(32) NOT NULL AUTO_INCREMENT,
       `username` varchar(255) DEFAULT NULL,
       `password` varchar(255) DEFAULT NULL,
       PRIMARY KEY (`id`)
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
     ```

4. 通过 Spring Boot 启动项目。

## 效果预览

### 主界面

- 主界面由用户名、密码和验证码三个输入框、验证码功能区和“注册”、“登录”两个按钮组成。当用户点击“看不清？点击图片刷新一下”时，会刷新出辨认难度更低的验证码图片，有关验证码的具体功能介绍与实现请见 [check-code/readme.md](https://github.com/XilinZhu/check-code).

  <img src="https://i.loli.net/2020/11/01/TLbzW9ENYUhepHs.png" style="zoom:50%;" />

### 注册页面

- 通过主界面的“注册”按钮重定向到注册页面 `regist.html`

  <img src="https://i.loli.net/2020/11/01/nNRWvJFI5PXcTAl.png" style="zoom:50%;" />



## 关于整合 MyBatis 框架

### 官方简介

MyBatis 是一款优秀的持久层框架，它支持自定义 SQL、存储过程以及高级映射。MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作。MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。

### 定义映射

MyBatis的配置文件包括两个大的部分，一是基础配置文件，一个是映射文件。在MyBatis 中也可以使用注解来实现映射，此方式的功能和可读性相对较弱，但在小项目的快速开发上十分便利。

此项目的映射配置文件如下：

```java
@Mapper //标记mapper文件位置，否则在Application.class启动类上配置mapper包扫描
@Repository
public interface UserMapper {

    /**
     * 查询用户名是否存在，若存在，不允许注册
     * @param username
     * @return
     */
    @Select(value = "select user.username,user.password from user where user.username=#{username}")
    User findUserByName(String username);

    /**
     * 注册  插入一条user记录
     * @param user
     * @return
     */
    @Insert("insert into user values(#{id},#{username},#{password})")
    //使用自增主键；加入该注解可以保存对象后，查看对象插入id
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void regist(User user);

    /**
     * 登录
     * @param user
     * @return
     */
    @Select("select user.id from user where user.username = #{username} and password = #{password}")
    Long login(User user);
}

```

### 定义服务类

通过映射，我们将 Java 变量与数据库属性一一对应了起来。在这之后，我们要通过一个服务类（或接口）来启动此映射。

```java
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    /**
     * 注册
     * @param user 参数封装
     * @return result 返回前端的信息
     */
    public Result regist(User user) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            User existUser = userMapper.findUserByName(user.getUsername());
            if(existUser != null){
                //如果用户名已存在
                result.setMsg("用户名已存在");

            }else{
                userMapper.regist(user);
                System.out.println(user.getId());
                result.setMsg("注册成功");
                result.setSuccess(true);
                result.setDetail(user);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 登录
     * @param user 用户名和密码
     * @return Result
     */
    public Result login(User user) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            Long userId= userMapper.login(user);
            if(userId == null){
                result.setMsg("用户名或密码错误");
            }else{
                result.setMsg("登录成功");
                result.setSuccess(true);
                user.setId(userId);
                result.setDetail(user);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
```

### 在控制类中调用服务

```java
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param user 参数封装
     * @return result 返回前端的信息
     */
    @PostMapping(value = "/regist")
    public Result regist(User user){
        return userService.regist(user);
    }

    /**
     * 登录
     * @param user 参数封装
     * @return result 返回前端的信息
     */
    @PostMapping(value = "/login")
    public Result login(User user){
        return userService.login(user);
    }
}

```

