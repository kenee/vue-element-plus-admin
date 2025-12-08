package com.example.admin.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * PasswordUtil 单元测试
 *
 * @author example
 */
public class PasswordUtilTest {

    @Test
    void testEncode() {
        // 测试密码加密
        String rawPassword = "admin123";
        String encodedPassword = PasswordUtil.encode(rawPassword);
        
        // 验证加密后的密码不为空
        assertNotNull(encodedPassword);
        // 验证加密后的密码长度大于原密码
        assertTrue(encodedPassword.length() > rawPassword.length());
        // 验证每次加密的结果都不同（BCrypt每次生成的盐不同）
        String encodedPassword2 = PasswordUtil.encode(rawPassword);
        assertNotEquals(encodedPassword, encodedPassword2);
    }

    @Test
    void testMatches() {
        // 测试密码验证
        String rawPassword = "admin123";
        String encodedPassword = PasswordUtil.encode(rawPassword);
        
        // 验证正确的密码匹配成功
        assertTrue(PasswordUtil.matches(rawPassword, encodedPassword));
        // 验证错误的密码匹配失败
        assertFalse(PasswordUtil.matches("wrongpassword", encodedPassword));
        // 验证空密码匹配失败
        assertFalse(PasswordUtil.matches("", encodedPassword));
    }

    @Test
    void testMatchesWithEmptyEncodedPassword() {
        // 测试空加密密码的情况
        assertFalse(PasswordUtil.matches("admin123", ""));
    }

}