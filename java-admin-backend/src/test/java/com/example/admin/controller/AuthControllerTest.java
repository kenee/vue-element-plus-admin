package com.example.admin.controller;

import com.example.admin.dto.LoginDto;
import com.example.admin.service.IAuthService;
import com.example.admin.utils.ResponseResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * AuthController 集成测试
 * 测试认证相关API接口
 * 
 * 对应前端API:
 * - POST /api/auth/login
 * - GET /api/auth/logout
 *
 * @author example
 */
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IAuthService authService;

    private LoginDto validLoginDto;
    private LoginDto invalidLoginDto;

    @BeforeEach
    void setUp() {
        // 准备有效的登录数据
        validLoginDto = new LoginDto();
        validLoginDto.setUsername("admin");
        validLoginDto.setPassword("admin123");

        // 准备无效的登录数据
        invalidLoginDto = new LoginDto();
        invalidLoginDto.setUsername("wronguser");
        invalidLoginDto.setPassword("wrongpass");
    }

    @Test
    void testLoginSuccess() throws Exception {
        // 准备模拟响应数据
        Map<String, Object> data = new HashMap<>();
        data.put("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...");
        data.put("username", "admin");
        data.put("userId", "1");

        ResponseResult<?> mockResponse = ResponseResult.success(data);

        // 模拟服务层行为
        when(authService.login(anyString(), anyString())).thenReturn(mockResponse);

        // 执行测试并验证
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validLoginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.token").exists())
                .andExpect(jsonPath("$.data.username").value("admin"));
    }

    @Test
    void testLoginWithInvalidCredentials() throws Exception {
        // 模拟登录失败
        ResponseResult<?> mockResponse = ResponseResult.error(401, "用户名或密码错误");

        when(authService.login(anyString(), anyString())).thenReturn(mockResponse);

        // 执行测试并验证
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidLoginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.message").value("用户名或密码错误"));
    }

    @Test
    void testLoginWithEmptyUsername() throws Exception {
        LoginDto emptyUsernameDto = new LoginDto();
        emptyUsernameDto.setUsername("");
        emptyUsernameDto.setPassword("password");

        ResponseResult<?> mockResponse = ResponseResult.error(400, "用户名不能为空");
        when(authService.login(anyString(), anyString())).thenReturn(mockResponse);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emptyUsernameDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400));
    }

    @Test
    void testLoginWithEmptyPassword() throws Exception {
        LoginDto emptyPasswordDto = new LoginDto();
        emptyPasswordDto.setUsername("admin");
        emptyPasswordDto.setPassword("");

        ResponseResult<?> mockResponse = ResponseResult.error(400, "密码不能为空");
        when(authService.login(anyString(), anyString())).thenReturn(mockResponse);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emptyPasswordDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400));
    }

    @Test
    void testLogout() throws Exception {
        // 模拟登出成功
        ResponseResult<?> mockResponse = ResponseResult.success();

        when(authService.logout()).thenReturn(mockResponse);

        // 执行测试并验证
        mockMvc.perform(post("/auth/logout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testLoginWithMalformedJson() throws Exception {
        // 测试格式错误的JSON
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{invalid json}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLoginWithNullBody() throws Exception {
        // 测试空请求体
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
