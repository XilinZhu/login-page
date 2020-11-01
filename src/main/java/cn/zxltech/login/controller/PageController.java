package cn.zxltech.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {
    @RequestMapping({"", "login"}) //默认进入登陆页面
    public String login() {
        return "login";
    }

    @RequestMapping(value = "regist", method = RequestMethod.POST) // 从登录界面跳转到注册页面
    public String regist(){
        return "regist";
    }
}
