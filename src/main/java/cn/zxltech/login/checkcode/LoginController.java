package cn.zxltech.login.checkcode;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
    @RequestMapping({"", "login"}) //默认进入登陆页面
    public String login() {
        return "login";
    }

    @RequestMapping(value = "regist", method = RequestMethod.POST)
    public String regist(){
        return "regist";
    }
}
