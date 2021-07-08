package cn.zz.Study.controller;

import cn.zz.Study.common.CustomizeException;
import cn.zz.Study.common.ErrorCode;
import cn.zz.Study.common.Result;
import cn.zz.Study.common.vo.LoginVO;
import cn.zz.Study.service.LoginService;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 使用Thymeleaf测试登录
 *
 * @author admin
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    private LoginService loginService;

    /**
     * 账号密码登录
     */
    @PostMapping("/password")
    public String password(@RequestBody LoginVO loginVO) {
        //非空判断
        if (ObjectUtils.isEmpty(loginVO) || loginVO.getPhone() == null || loginVO.getPassWord() == null) {
            throw new CustomizeException(ErrorCode.PARAMETER_ERROR);
        }
        return Result.ok(loginService.login(loginVO));
    }

    /**
     * 邮件测试
     */
    @GetMapping("/test")
    public String test(){
        return Result.ok(1/0);
    }
}

