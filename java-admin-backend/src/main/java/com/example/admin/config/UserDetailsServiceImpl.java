package com.example.admin.config;

import com.example.admin.entity.SysUser;
import com.example.admin.entity.SysRole;
import com.example.admin.entity.SysRoleMenu;
import com.example.admin.entity.SysMenu;
import com.example.admin.service.ISysUserService;
import com.example.admin.repository.SysRoleMenuRepository;
import com.example.admin.repository.SysMenuRepository;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserDetailsService实现类
 *
 * @author example
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ISysUserService sysUserService;
    
    @Autowired
    private SysRoleMenuRepository sysRoleMenuRepository;
    
    @Autowired
    private SysMenuRepository sysMenuRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        // 加载用户权限
        List<SimpleGrantedAuthority> authorities = loadUserAuthorities(user.getId());

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .disabled(user.getStatus() == 0)
                .authorities(authorities)
                .build();
    }
    
    /**
     * 加载用户权限
     * @param userId 用户ID
     * @return 用户权限列表
     */
    private List<SimpleGrantedAuthority> loadUserAuthorities(String userId) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        
        // 获取用户角色
        List<SysRole> roles = sysUserService.findRolesByUserId(userId);
        if (roles.isEmpty()) {
            return authorities;
        }
        
        // 获取角色ID列表
        List<String> roleIds = roles.stream().map(SysRole::getId).collect(Collectors.toList());
        
        // 查询所有角色关联的菜单ID
        QueryWrapper<SysRoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.in("role_id", roleIds);
        List<SysRoleMenu> roleMenus = sysRoleMenuRepository.selectList(roleMenuQueryWrapper);
        
        if (roleMenus.isEmpty()) {
            return authorities;
        }
        
        // 获取菜单ID列表
        List<String> menuIds = roleMenus.stream().map(SysRoleMenu::getMenuId).distinct().collect(Collectors.toList());
        
        // 查询所有菜单
        QueryWrapper<SysMenu> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.in("id", menuIds);
        List<SysMenu> menus = sysMenuRepository.selectList(menuQueryWrapper);
        
        // 提取权限标识
        for (SysMenu menu : menus) {
            if (menu.getPermission() != null && !menu.getPermission().isEmpty()) {
                authorities.add(new SimpleGrantedAuthority(menu.getPermission()));
            }
        }
        
        return authorities;
    }

}