package cn.zz.Study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 使用Thymeleaf测试登录
 * @author admin
 */
@Controller
public class LoginController {

    @RequestMapping("/hello")
    public String index(){
        return "login";
    }

}

