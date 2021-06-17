package cn.zz.Study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@EnableWebSecurity
/**
 * 开启@EnableGlobalMethodSecurity(prePostEnabled = true)注解
 * 在继承 WebSecurityConfigurerAdapter 这个类的类上面贴上这个注解
 * 并且prePostEnabled设置为true,@PreAuthorize这个注解才能生效
 * SpringSecurity默认是关闭注解功能的.
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 配置密码解析
     * @return
     */
    @Bean
    protected PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置用户名和密码
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //开启跨域
        http.cors()
                //关闭csrf防护 >只有关闭了,才能接受来自表单的请求
                .and().csrf().disable()
                //使用Spring自带的登录页，登陆成功跳转的页面
                .formLogin().successForwardUrl("")
                .and()
                //开启授权请求
                .authorizeRequests()
                //拦截所有请求
                .anyRequest()
                //所有请求都需要身份验证
                .authenticated()

        ;




        http.csrf().disable();
    }
}
