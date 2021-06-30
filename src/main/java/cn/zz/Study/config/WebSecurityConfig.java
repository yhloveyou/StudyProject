package cn.zz.Study.config;

import cn.zz.Study.config.interceptor.JwtVerificationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
//    @Resource
//    private UserDetailsService userDetailsService;

    @Resource
    private JwtVerificationFilter jwtVerificationFilter;

//    /**
//     * 配置密码解析
//     * @return
//     */
//    @Bean
//    protected PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    /**
//     * 配置用户名和密码
//     * @param auth
//     * @throws Exception
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭csrf防护 >只有关闭了,才能接受来自表单的请求
        http.csrf().disable()
                .cors()//开启跨域
                .and()

//使用token时需禁用Session，和表单登录冲突，表单登录需要session，因此二选一。
//                //使用Spring自带的登录页，登陆成功跳转的页面（必须是Post请求）
//                .formLogin()
//                //使用自定义登录页面，不需要的话，注释该行
//                .loginPage("/hello")
//                //指定自定义form表单请求的路径，接受登录请求的参数。
//                //账号密码名字必须是 username，和password不然SpringSecurity扫描不到
//                .loginProcessingUrl("/authentication/form")
//                //登陆成功跳转
//                .successForwardUrl("/index").and()



                //开启授权请求
                .authorizeRequests()
                //放行接口，因为使用自定义登录页面所以需要放行
                .antMatchers("/login/**").permitAll()
                //拦截所有请求，所有请求都需要登录认证
                .anyRequest().authenticated()
                .and()
                .addFilterAfter(jwtVerificationFilter, UsernamePasswordAuthenticationFilter.class)
                //前后端分离采用JWT 不需要session（添加后Spring永远不会创建session）
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
