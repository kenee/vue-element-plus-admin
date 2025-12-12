package com.example.admin.filter;

import com.example.admin.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT认证过滤器
 *
 * @author example
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取Authorization头
        String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String userId = null;
        String token = null;

        // 检查Authorization头是否存在
        if (authorizationHeader != null) {
            // 支持两种Token格式："Bearer <token>" 和直接传递token
            if (authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
            } else {
                token = authorizationHeader;
            }
            try {
                // 从token中获取用户名
                username = jwtUtil.getUsernameFromToken(token);
                // 从token中获取用户ID
                userId = jwtUtil.getUserIdFromToken(token);
            } catch (Exception e) {
                // token无效
                logger.error("JWT token invalid: {}", e.getMessage());
            }
        }

        // 如果用户名不为空且上下文没有认证信息
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 加载用户信息
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 验证token
            if (jwtUtil.validateToken(token, userDetails.getUsername())) {
                // 创建认证令牌
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 设置认证信息到上下文
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 继续执行过滤器链
        filterChain.doFilter(request, response);
    }

}