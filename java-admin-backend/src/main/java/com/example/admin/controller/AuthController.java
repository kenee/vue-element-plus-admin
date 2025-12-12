package com.example.admin.controller;

import com.example.admin.dto.LoginDto;
import com.example.admin.service.IAuthService;
import com.example.admin.utils.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证控制器
 *
 * @author example
 */
@Tag(name = "auth", description = "认证相关接口")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @Operation(summary = "用户登录", description = "用户登录，返回JWT token")
    @PostMapping("/login")
    public ResponseResult<?> login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto.getUsername(), loginDto.getPassword());
    }

    @Operation(summary = "用户登出", description = "用户登出")
    @GetMapping("/logout")
    public ResponseResult<?> logout() {
        return authService.logout();
    }

}