package com.example.admin.service;

import com.example.admin.entity.SysUser;
import com.example.admin.utils.ResponseResult;

import java.util.Map;

/**
 * 认证服务
 *
 * @author example
 */
public interface IAuthService {

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return ResponseResult
     */
    ResponseResult<Map<String, Object>> login(String username, String password);

    /**
     * 用户登出
     *
     * @return ResponseResult
     */
    ResponseResult<Void> logout();

    /**
     * 刷新token
     *
     * @param refreshToken 刷新token
     * @return ResponseResult
     */
    ResponseResult<Map<String, Object>> refreshToken(String refreshToken);

    /**
     * 生成token
     *
     * @param user 用户
     * @return Map<String, Object>
     */
    Map<String, Object> generateToken(SysUser user);

}