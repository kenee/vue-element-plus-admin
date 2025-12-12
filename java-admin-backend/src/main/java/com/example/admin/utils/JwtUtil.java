package com.example.admin.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 *
 * @author example
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret:default-secret-key-change-me-in-production}")
    private String secret;

    @Value("${jwt.expiration:86400000}")
    private Long expiration;

    @Value("${jwt.refresh-token-expiration:604800000}")
    private Long refreshTokenExpiration;

    /**
     * 生成token
     *
     * @param userId   用户ID
     * @param username 用户名
     * @return token
     */
    public String generateToken(String userId, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userId);
        claims.put("username", username);
        return generateToken(userId, username, claims);
    }

    /**
     * 生成token
     *
     * @param userId   用户ID
     * @param username 用户名
     * @param claims   声明
     * @return token
     */
    public String generateToken(String userId, String username, Map<String, Object> claims) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId) // Subject设置为用户ID，与gin-backend-admin对齐
                .claim("id", userId)
                .claim("username", username)
                .setIssuer("gin-admin")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * 生成刷新token
     *
     * @param subject 主题（用户名）
     * @return 刷新token
     */
    public String generateRefreshToken(String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenExpiration);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    /**
     * 从token中获取用户名
     *
     * @param token token
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        // 优先从username声明中获取，兼容gin-backend-admin
        String username = (String) claims.get("username");
        if (username == null) {
            // 兼容旧版Token，从Subject获取
            username = claims.getSubject();
        }
        return username;
    }

    /**
     * 从token中获取用户ID
     *
     * @param token token
     * @return 用户ID
     */
    public String getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        // 优先从id声明中获取
        String userId = (String) claims.get("id");
        if (userId == null || userId.isEmpty()) {
            // 如果id声明为空，使用Subject作为用户ID，与gin-backend-admin对齐
            userId = claims.getSubject();
        }
        return userId;
    }

    /**
     * 从token中获取声明
     *
     * @param token token
     * @return Claims
     */
    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 验证token是否过期
     *
     * @param token token
     * @return 是否过期
     */
    public boolean isTokenExpired(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration().before(new Date());
    }

    /**
     * 获取token过期时间
     *
     * @return 过期时间（毫秒）
     */
    public Long getExpiration() {
        return expiration;
    }

    /**
     * 获取刷新token过期时间
     *
     * @return 过期时间（毫秒）
     */
    public Long getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }

    /**
     * 验证token
     *
     * @param token       token
     * @param username    用户名
     * @return 是否有效
     */
    public boolean validateToken(String token, String username) {
        String tokenUsername = getUsernameFromToken(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }
}