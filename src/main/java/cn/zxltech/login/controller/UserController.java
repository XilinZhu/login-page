package cn.zxltech.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.zxltech.login.bean.Result;
import cn.zxltech.login.bean.User;
import cn.zxltech.login.service.UserService;

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


