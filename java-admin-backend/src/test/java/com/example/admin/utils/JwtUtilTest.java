package com.example.admin.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JwtUtil 单元测试
 *
 * @author example
 */
public class JwtUtilTest {

    private JwtUtil jwtUtil;
    private String testSecret;
    private Long testExpiration;
    private Long testRefreshTokenExpiration;

    @BeforeEach
    void setUp() {
        // 初始化JwtUtil，手动设置属性，不依赖Spring上下文
        jwtUtil = new JwtUtil();
        // 使用一个足够长的密钥（至少256位）
        testSecret = "this-is-a-very-long-secret-key-that-is-at-least-256-bits-long-for-secure-jwt-signing";
        testExpiration = 86400000L; // 24小时
        testRefreshTokenExpiration = 604800000L; // 7天
        
        // 使用反射设置私有属性
        try {
            java.lang.reflect.Field secretField = JwtUtil.class.getDeclaredField("secret");
            secretField.setAccessible(true);
            secretField.set(jwtUtil, testSecret);
            
            java.lang.reflect.Field expirationField = JwtUtil.class.getDeclaredField("expiration");
            expirationField.setAccessible(true);
            expirationField.set(jwtUtil, testExpiration);
            
            java.lang.reflect.Field refreshTokenExpirationField = JwtUtil.class.getDeclaredField("refreshTokenExpiration");
            refreshTokenExpirationField.setAccessible(true);
            refreshTokenExpirationField.set(jwtUtil, testRefreshTokenExpiration);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to initialize JwtUtil: " + e.getMessage());
        }
    }

    @Test
    void testGenerateToken() {
        // 测试生成token
        String username = "testuser";
        String token = jwtUtil.generateToken(username);
        assertNotNull(token);
        assertFalse(token.isEmpty());
        
        // 验证token可以被解析
        Claims claims = parseToken(token);
        assertEquals(username, claims.getSubject());
    }

    @Test
    void testGenerateTokenWithClaims() {
        // 测试带自定义声明的token生成
        String username = "testuser";
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "admin");
        claims.put("userId", "123456");
        
        String token = jwtUtil.generateToken(username, claims);
        assertNotNull(token);
        
        // 验证声明是否被正确设置
        Claims parsedClaims = parseToken(token);
        assertEquals(username, parsedClaims.getSubject());
        assertEquals("admin", parsedClaims.get("role"));
        assertEquals("123456", parsedClaims.get("userId"));
    }

    @Test
    void testGenerateRefreshToken() {
        // 测试生成刷新token
        String username = "testuser";
        String refreshToken = jwtUtil.generateRefreshToken(username);
        assertNotNull(refreshToken);
        assertFalse(refreshToken.isEmpty());
        
        // 验证刷新token可以被解析
        Claims claims = parseToken(refreshToken);
        assertEquals(username, claims.getSubject());
    }

    @Test
    void testGetUsernameFromToken() {
        // 测试从token中获取用户名
        String username = "testuser";
        String token = jwtUtil.generateToken(username);
        
        String parsedUsername = jwtUtil.getUsernameFromToken(token);
        assertEquals(username, parsedUsername);
    }

    @Test
    void testIsTokenExpired() {
        // 测试token过期检查
        String username = "testuser";
        String token = jwtUtil.generateToken(username);
        
        // 刚生成的token不应该过期
        assertFalse(jwtUtil.isTokenExpired(token));
    }

    @Test
    void testGetClaimsFromToken() {
        // 测试从token中获取声明
        String username = "testuser";
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "admin");
        
        String token = jwtUtil.generateToken(username, claims);
        Claims parsedClaims = jwtUtil.getClaimsFromToken(token);
        
        assertNotNull(parsedClaims);
        assertEquals(username, parsedClaims.getSubject());
        assertNotNull(parsedClaims.getIssuedAt());
        assertNotNull(parsedClaims.getExpiration());
        assertEquals("admin", parsedClaims.get("role"));
    }

    @Test
    void testValidateToken() {
        // 测试token验证
        String username = "testuser";
        String token = jwtUtil.generateToken(username);
        
        // 验证正确的用户名和token匹配成功
        assertTrue(jwtUtil.validateToken(token, username));
        
        // 验证错误的用户名匹配失败
        assertFalse(jwtUtil.validateToken(token, "wronguser"));
    }

    /**
     * 辅助方法：解析token
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(testSecret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
