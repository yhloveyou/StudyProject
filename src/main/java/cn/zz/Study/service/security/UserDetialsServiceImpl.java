package cn.zz.Study.service.security;

import cn.zz.Study.entity.Permission;
import cn.zz.Study.entity.Role;
import cn.zz.Study.entity.RolePermission;
import cn.zz.Study.service.PermissionService;
import cn.zz.Study.service.RolePermissionService;
import cn.zz.Study.service.RoleService;
import cn.zz.Study.service.UserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserDetailsService接口用于返回用户相关数据。它有loadUserByUsername()方法
 * 根据username查询用户实体，可以实现该接口覆盖该方法，实现自定义获取用户过程。
 * 该接口实现类被DaoAuthenticationProvider 类使用，用于认证过程中载入用户信息。
 * 不使用token时可这么使用
 * @author admin
 */
@Service
public class UserDetialsServiceImpl implements UserDetailsService {
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RolePermissionService rolePermissionService;
    @Resource
    private UserService userService;
    /**
     * 通过UserName去数据库查询账号密码
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //1、拿到用户的账号，根据账号查询数据
        cn.zz.Study.entity.User user = userService.getOne(Wrappers.<cn.zz.Study.entity.User>lambdaQuery()
                .eq(cn.zz.Study.entity.User::getUserPhone, userName));

        //2、拿到用户的角色和权限
        Role role = roleService.getOne(Wrappers.<Role>lambdaQuery().eq(Role::getUserId, 1));
        List<Long> premissionId = rolePermissionService.list(Wrappers.<RolePermission>lambdaQuery().eq(RolePermission::getRoleId, role.getId()))
                .stream()
                .map(RolePermission::getPremissionId)
                .collect(Collectors.toList());
        List<Permission> permissions = permissionService.list(Wrappers.<Permission>lambdaQuery().in(Permission::getId, premissionId));

        //3、返回的权限
        List<GrantedAuthority> authorityList = new ArrayList<>();

        //4、查出权限列表循环放入  authorityList  中
        for (Permission permission : permissions) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission.getPermissionUrl());
            authorityList.add(simpleGrantedAuthority);
        }

//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + "admin");
//        authorityList.add(authority);
//        SimpleGrantedAuthority authority1 = new SimpleGrantedAuthority("study:add");
//        authorityList.add(authority1);

        return new User(user.getUserPhone(),user.getUserPassword(),authorityList);
    }
}
