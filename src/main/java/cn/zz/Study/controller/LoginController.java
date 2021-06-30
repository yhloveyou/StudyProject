package cn.zz.Study.controller;

import cn.zz.Study.service.LoginService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 使用Thymeleaf测试登录
 * @author admin
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    private LoginService loginService;

    @GetMapping("/password/{phone}/{password}")
    public String password(@PathVariable String phone,@PathVariable String password){
        return loginService.login(phone,password);
    }

}

