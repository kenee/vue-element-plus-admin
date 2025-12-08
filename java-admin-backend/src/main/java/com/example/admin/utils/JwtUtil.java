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

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    /**
     * 生成token
     *
     * @param subject 主题（用户名）
     * @return token
     */
    public String generateToken(String subject) {
        return generateToken(subject, new HashMap<>());
    }

    /**
     * 生成token
     *
     * @param subject    主题（用户名）
     * @param claims     声明
     * @return token
     */
    public String generateToken(String subject, Map<String, Object> claims) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
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
        return claims.getSubject();
    }

    /**
     * 从token中获取声明
     *
     * @param token token
     * @return Claims
     */
    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
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