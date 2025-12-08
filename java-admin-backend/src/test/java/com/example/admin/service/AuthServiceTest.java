package com.example.admin.service;

import com.example.admin.entity.SysUser;
import com.example.admin.service.impl.AuthServiceImpl;
import com.example.admin.service.impl.SysUserServiceImpl;
import com.example.admin.utils.JwtUtil;
import com.example.admin.utils.ResponseResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * AuthService 单元测试
 *
 * @author example
 */
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private ISysUserService sysUserService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // 清除SecurityContext，避免测试之间的干扰
        SecurityContextHolder.clearContext();
    }

    @Test
    void testLoginSuccess() {
        // 准备测试数据
        String username = "testuser";
        String password = "testpassword";
        SysUser mockUser = new SysUser();
        mockUser.setId("123");
        mockUser.setUsername(username);
        mockUser.setPassword(password);

        // 模拟依赖
        Authentication mockAuth = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mockAuth);
        when(sysUserService.findByUsername(username)).thenReturn(mockUser);
        
        // 模拟JWT生成
        Map<String, Object> mockTokenMap = new HashMap<>();
        mockTokenMap.put("accessToken", "mock-token");
        mockTokenMap.put("refreshToken", "mock-refresh-token");
        when(jwtUtil.generateToken(username)).thenReturn("mock-token");
        when(jwtUtil.generateRefreshToken(username)).thenReturn("mock-refresh-token");

        // 执行测试
        ResponseResult<Map<String, Object>> result = authService.login(username, password);

        // 验证结果
        assertTrue(result.isSuccess());
        assertNotNull(result.getData());
        assertEquals("mock-token", result.getData().get("accessToken"));
        assertEquals("mock-refresh-token", result.getData().get("refreshToken"));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(sysUserService).findByUsername(username);
        verify(jwtUtil).generateToken(username);
        verify(jwtUtil).generateRefreshToken(username);
    }

    @Test
    void testLoginFailure() {
        // 准备测试数据
        String username = "testuser";
        String password = "wrongpassword";

        // 模拟认证失败
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Authentication failed"));

        // 执行测试并验证异常
        assertThrows(RuntimeException.class, () -> authService.login(username, password));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(sysUserService, never()).findByUsername(anyString());
        verify(jwtUtil, never()).generateToken(anyString());
    }

    @Test
    void testLogout() {
        // 执行登出
        ResponseResult<Void> result = authService.logout();

        // 验证结果
        assertTrue(result.isSuccess());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testRefreshToken() {
        // 准备测试数据
        String refreshToken = "mock-refresh-token";
        String username = "testuser";
        SysUser mockUser = new SysUser();
        mockUser.setId("123");
        mockUser.setUsername(username);

        // 模拟依赖
        when(jwtUtil.getUsernameFromToken(refreshToken)).thenReturn(username);
        when(sysUserService.findByUsername(username)).thenReturn(mockUser);
        when(jwtUtil.generateToken(username)).thenReturn("new-mock-token");
        when(jwtUtil.generateRefreshToken(username)).thenReturn("new-mock-refresh-token");

        // 执行测试
        ResponseResult<Map<String, Object>> result = authService.refreshToken(refreshToken);

        // 验证结果
        assertTrue(result.isSuccess());
        assertNotNull(result.getData());
        verify(jwtUtil).getUsernameFromToken(refreshToken);
        verify(sysUserService).findByUsername(username);
        verify(jwtUtil).generateToken(username);
        verify(jwtUtil).generateRefreshToken(username);
    }

    @Test
    void testGenerateToken() {
        // 准备测试数据
        SysUser mockUser = new SysUser();
        mockUser.setId("123");
        mockUser.setUsername("testuser");

        // 模拟JWT生成
        when(jwtUtil.generateToken("testuser")).thenReturn("mock-token");
        when(jwtUtil.generateRefreshToken("testuser")).thenReturn("mock-refresh-token");
        when(jwtUtil.getExpiration()).thenReturn(86400000L);

        // 执行测试
        Map<String, Object> tokenMap = authService.generateToken(mockUser);

        // 验证结果
        assertNotNull(tokenMap);
        assertEquals("mock-token", tokenMap.get("accessToken"));
        assertEquals("mock-refresh-token", tokenMap.get("refreshToken"));
        assertEquals("Bearer", tokenMap.get("tokenType"));
        assertEquals(86400000L, tokenMap.get("expiresIn"));
        assertEquals(mockUser, tokenMap.get("user"));
        verify(jwtUtil).generateToken("testuser");
        verify(jwtUtil).generateRefreshToken("testuser");
        verify(jwtUtil).getExpiration();
    }
}
