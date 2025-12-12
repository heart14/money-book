package com.liiwe.moneybook.base.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author lwf14
 * @since 2025/12/11 16:37
 */
@Slf4j
@Component
public class JwtRefreshTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final StringRedisTemplate redisTemplate;
    private final UserDetailsService userDetailsService;

    public JwtRefreshTokenFilter(JwtUtils jwtUtils,
                              StringRedisTemplate redisTemplate,
                              UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.redisTemplate = redisTemplate;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 如果不是刷新token请求，直接放行，继续执行后续过滤链
        if (!"/auth/refresh".equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        // 如果是刷新token请求，则开始进行token验证
        String header = request.getHeader("Re-Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String jwtToken = header.substring(7);
        if (!jwtUtils.validateToken(jwtToken)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Claims claims = jwtUtils.parseRefreshToken(jwtToken);
        String uid = claims.getSubject();
        String jti = claims.getId();

        // 检查refresh_token是否在redis中，不存在则说明token已被撤销或伪造
        String redisJti = redisTemplate.opsForValue().get("refresh_token:" + uid);
        if (!jti.equals(redisJti)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String username = claims.getIssuer();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }
}
