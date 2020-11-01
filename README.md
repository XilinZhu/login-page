# login-page
A login page based on Spring boot and MyBatis. The check code function is based on Kaptha and java.awt.

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

- 主界面由用户名、密码和验证码三个输入框、验证码功能区和“注册”、“登录”两个按钮组成。当用户点击“看不清？点击图片刷新一下”时，会刷新出辨认难度更低的验证码图片，具体功能介绍与实现请见 [check-code/readme.md](https://github.com/XilinZhu/check-code).

  <img src="https://i.loli.net/2020/11/01/TLbzW9ENYUhepHs.png" style="zoom:50%;" />

### 注册页面

- <img src="https://i.loli.net/2020/11/01/nNRWvJFI5PXcTAl.png" style="zoom:50%;" />