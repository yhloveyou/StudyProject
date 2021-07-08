package cn.zz.Study.service.impl;

import cn.zz.Study.common.CustomizeException;
import cn.zz.Study.common.ErrorCode;
import cn.zz.Study.common.RedisPrefixKey;
import cn.zz.Study.common.vo.LoginVO;
import cn.zz.Study.entity.User;
import cn.zz.Study.service.LoginService;
import cn.zz.Study.service.UserService;
import cn.zz.Study.utils.JwtUtils;
import cn.zz.Study.utils.MD5Utils;
import cn.zz.Study.utils.RedisUtils;
import cn.zz.Study.utils.customize.LoginNumUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author admin
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private UserService userService;


    /**
     * 账号密码登录
     */
    @Override
    public String login(LoginVO loginVO) {
        //MD5加密密码
        String md5Password = MD5Utils.MD5(loginVO.getPassWord());

        //得到User对象
        User user = userService.getOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUserPhone, loginVO.getPhone()));

        //非空检验
        if (ObjectUtils.isEmpty(user)) {
            throw new CustomizeException(ErrorCode.LOGIN_ERROR);
        }

        //登录次数限制
        if (!user.getUserPassword().equals(md5Password)) {
            LoginNumUtils.loginFail(user.getId());
            throw new CustomizeException(ErrorCode.PASSWORD_ERROR);
        }

        //不管Redis是否存在Token 都创建Token 已存在Token再去创建是因为做刷新Token操作
        String token = JwtUtils.createToken(String.valueOf(user.getId()), user.getUserName());
        RedisUtils.set(RedisPrefixKey
                .LOGIN_TOKEN
                .keyAppend(user.getId())
                .getKey(), token, RedisPrefixKey.LOGIN_TOKEN.getExpiredTime());
        return token;
    }
}
