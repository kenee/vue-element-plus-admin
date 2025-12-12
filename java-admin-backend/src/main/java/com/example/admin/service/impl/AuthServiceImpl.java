package com.example.admin.service.impl;

import com.example.admin.entity.SysUser;
import com.example.admin.service.IAuthService;
import com.example.admin.service.ISysUserService;
import com.example.admin.utils.JwtUtil;
import com.example.admin.utils.PasswordUtil;
import com.example.admin.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.admin.entity.SysRole;

/**
 * 认证服务实现类
 *
 * @author example
 */
@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseResult<Map<String, Object>> login(String username, String password) {
        // 验证用户名和密码
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // 将认证信息存入上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 获取用户信息
        SysUser user = sysUserService.findByUsername(username);

        // 生成token
        Map<String, Object> tokenMap = generateToken(user);

        return ResponseResult.success(tokenMap);
    }

    @Override
    public ResponseResult<Void> logout() {
        // 清除上下文
        SecurityContextHolder.clearContext();
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Map<String, Object>> refreshToken(String refreshToken) {
        // 从刷新token中获取用户名
        String username = jwtUtil.getUsernameFromToken(refreshToken);
        
        // 获取用户信息
        SysUser user = sysUserService.findByUsername(username);
        
        // 生成新的token
        Map<String, Object> tokenMap = generateToken(user);
        
        return ResponseResult.success(tokenMap);
    }

    @Override
    public Map<String, Object> generateToken(SysUser user) {
        // 生成token，使用userId和username，与gin-backend-admin对齐
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        
        // 获取用户角色列表
        List<SysRole> roles = sysUserService.findRolesByUserId(user.getId());
        
        // 构建角色信息（取第一个角色，与gin-admin-backend保持一致）
        String roleValue = "";
        String roleId = "";
        if (roles != null && !roles.isEmpty()) {
            SysRole role = roles.get(0);
            roleValue = role.getRoleValue();
            roleId = role.getId();
        }

        // 构建响应数据，与gin-admin-backend保持一致
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("access_token", token);
        tokenMap.put("username", user.getUsername());
        tokenMap.put("role", roleValue);
        tokenMap.put("roleId", roleId);
        tokenMap.put("id", user.getId());

        return tokenMap;
    }

}