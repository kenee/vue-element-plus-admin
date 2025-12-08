package com.example.admin.config;

import com.example.admin.entity.SysUser;
import com.example.admin.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserDetailsService实现类
 *
 * @author example
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        // TODO: 加载用户权限
        // List<GrantedAuthority> authorities = new ArrayList<>();

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .disabled(user.getStatus() == 0)
                .build();
    }

}