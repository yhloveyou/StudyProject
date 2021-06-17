package cn.zz.Study.config;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

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
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //开启跨域
        http.cors();



        //关闭csrf防护 >只有关闭了,才能接受来自表单的请求
        http.csrf().disable();
    }
}
