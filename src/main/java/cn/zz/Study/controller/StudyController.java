package cn.zz.Study.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author admin
 * 手写测试Controller
 * 用来测试权限管理
 */
@RestController
public class StudyController {

    @PostMapping("/index")
    public String index(){
        return "登陆成功    ==>>    进入主页";
    }

    @PreAuthorize("hasAuthority('study:add')")
    @GetMapping("/main")
    public String main(){
        return "得到授权    ==>>    进入页面";
    }

    @PreAuthorize("hasAnyRole('study:remove')")
    @GetMapping("/delete")
    public String delete(){
        return "得到授权    ==>>    删除操作";
    }

    @GetMapping("/add")
    public String add(){
        return "访问添加页面";
    }
}
