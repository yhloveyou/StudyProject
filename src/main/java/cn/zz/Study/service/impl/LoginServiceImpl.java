package cn.zz.Study.service.impl;

import cn.zz.Study.common.RedisPrefixKey;
import cn.zz.Study.entity.User;
import cn.zz.Study.service.LoginService;
import cn.zz.Study.service.UserService;
import cn.zz.Study.util.JwtUtils;
import cn.zz.Study.util.MD5Utils;
import cn.zz.Study.util.RedisUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private UserService userService;


    /**
     * 账号密码登录
     * @param phone
     * @param password
     * @return
     */
    @Override
    public String login(String phone, String password) {
        //MD5加密密码
        String md5Password = MD5Utils.MD5(password);
        //得到User对象
        User user = userService.getOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUserPassword, md5Password)
                .eq(User::getUserPhone, phone));
        //非空检验
        if (ObjectUtils.isEmpty(user)) {
            return "不存在该账号密码";
        }
        //不管Redis是否存在Token 都创建Token 已存在Token再去创建是因为做刷新Token操作
        String token = JwtUtils.createToken(String.valueOf(user.getId()), user.getUserName());
        RedisUtils.set(RedisPrefixKey.LOGIN_TOKEN.keyAppend(user.getId()).getKey(),token,RedisPrefixKey.LOGIN_TOKEN.getExpiredTime());
        return token;
    }
}
