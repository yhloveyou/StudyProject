package cn.zz.Study.config.interceptor;

import cn.zz.Study.entity.Permission;
import cn.zz.Study.entity.Role;
import cn.zz.Study.entity.RolePermission;
import cn.zz.Study.service.PermissionService;
import cn.zz.Study.service.RolePermissionService;
import cn.zz.Study.service.RoleService;
import cn.zz.Study.util.JwtUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author admin
 * 过滤器 发起请求前检验Token 实现并在每次请求时只执行一次过滤
 */
@Component
@Slf4j
public class JwtVerificationFilter extends OncePerRequestFilter {
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RolePermissionService rolePermissionService;

    /**
     * 过滤器，检验Token
     * 发起请求时会调用两次，第二次是展示/favicon.ico
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, @NotNull FilterChain filterChain) throws ServletException, IOException {
        //获取Token
        String token = httpServletRequest.getHeader("token");
        //检验Token
        if (token == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        //获取权限                                                             根据Token获取载荷的值
        List<GrantedAuthority> authorityList = this.findAllAuthority(Long.valueOf(JwtUtils.getAudience(token)));

        //安全上下文，存储认证授权的相关信息，实际上就是存储"当前用户"账号信息和相关权限
        SecurityContextHolder
                .getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(null,null,authorityList));


        //将请求转发给过滤器链下一个filter
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * 查找权限
     * @param userId
     * @return
     */
    private List<GrantedAuthority> findAllAuthority(Long userId){
        //1、拿到用户的角色和权限
        Role role = roleService.getOne(Wrappers.<Role>lambdaQuery().eq(Role::getUserId, userId));
        List<Long> premissionId = rolePermissionService.list(Wrappers.<RolePermission>lambdaQuery().eq(RolePermission::getRoleId, role.getId()))
                .stream()
                .map(RolePermission::getPremissionId)
                .collect(Collectors.toList());
        List<Permission> permissions = permissionService.list(Wrappers.<Permission>lambdaQuery().in(Permission::getId, premissionId));

        //2、返回的权限
        List<GrantedAuthority> authorityList = new ArrayList<>();

        //3、查出权限列表循环放入  authorityList  中
        for (Permission permission : permissions) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission.getPermissionUrl());
            authorityList.add(simpleGrantedAuthority);
        }
        return authorityList;
    }
}