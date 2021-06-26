package cn.zz.Study.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author admin
 * 过滤器 发起请求前检验Token 实现并在每次请求时只执行一次过滤
 */
@Component
@Slf4j
public class JwtVerificationFilter extends OncePerRequestFilter {

    /**
     * 过滤器，检验Token
     * 发起请求时会调用两次，第二次是展示/favicon.ico
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("token");
        if (token==null){
            new Exception("异常");
        }
        System.out.println(token);
        //将请求转发给过滤器链下一个filter
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
