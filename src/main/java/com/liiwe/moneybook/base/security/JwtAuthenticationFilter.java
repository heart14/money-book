package com.liiwe.moneybook.base.security;

// 导入必要的Servlet过滤器相关类

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author wfli
 * @since 2025/6/10 10:52
 */
// 使用Slf4j注解启用日志记录功能
@Slf4j
// 使用Component注解将该类标记为Spring管理的组件
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // 注入JwtUtils工具类实例
    private final JwtUtils jwtUtils;

    // 注入UserDetailsService服务实例
    private final UserDetailsService userDetailsService;

    // 构造函数，用于依赖注入
    public JwtAuthenticationFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    // 重写doFilterInternal方法，实现具体的过滤逻辑
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 获取请求头中的Authorization字段
        final String authorization = request.getHeader("Authorization");
        // 检查Authorization字段是否存在且以"Bearer "开头
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            // 如果不存在或格式不正确，记录错误日志并继续过滤链
            log.error("Authorization missed.");
            filterChain.doFilter(request, response);
            return;
        }

        // 移除"Bearer "前缀，获取JWT令牌
        final String jwtToken = authorization.substring(7);
        // 从JWT令牌中提取用户名
        final String username = jwtUtils.extractUsernameFromToken(jwtToken);

        // 检查用户名是否为空且当前安全上下文中没有认证信息
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 记录日志
            log.info("log authFilter loadUserByUsername");
            // 根据用户名加载用户详细信息
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 创建认证令牌
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            // 设置认证详情
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // 将认证令牌设置到安全上下文中
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        // 继续过滤链
        filterChain.doFilter(request, response);
    }
}
