package cn.zz.Study.service.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserDetailsService接口用于返回用户相关数据。它有loadUserByUsername()方法
 * 根据username查询用户实体，可以实现该接口覆盖该方法，实现自定义获取用户过程。
 * 该接口实现类被DaoAuthenticationProvider 类使用，用于认证过程中载入用户信息。
 * @author admin
 */
@Service
public class UserDetialsServiceImpl implements UserDetailsService {

    /**
     * 通过UserName去数据库查询账号密码
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return null;
    }
}
