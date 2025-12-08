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
import java.util.Map;

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
        // 生成token
        String token = jwtUtil.generateToken(user.getUsername());
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        // 构建响应数据
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", token);
        tokenMap.put("refreshToken", newRefreshToken);
        tokenMap.put("tokenType", "Bearer");
        tokenMap.put("expiresIn", jwtUtil.getExpiration());
        tokenMap.put("user", user);

        return tokenMap;
    }

}