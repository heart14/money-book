package com.liiwe.moneybook.base.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 安全配置类，用于配置Spring Security的相关设置
 * @author wfli
 * @since 2025/6/10 10:50
 */
@Configuration // 标记该类为配置类
@EnableWebSecurity // 启用Web安全配置
public class SecurityConfig {

    private final JwtAuthEntryPoint jwtAuthEntryPoint; // JWT认证入口点

    private final JwtAuthenticationFilter jwtAuthenticationFilter; // JWT认证过滤器

    public SecurityConfig(JwtAuthEntryPoint jwtAuthEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthEntryPoint = jwtAuthEntryPoint; // 注入JWT认证入口点
        this.jwtAuthenticationFilter = jwtAuthenticationFilter; // 注入JWT认证过滤器
    }

    @Bean // 定义一个Bean，返回SecurityFilterChain对象
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // 禁用CSRF保护
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint)) // 设置未授权访问的处理入口点
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 设置会话管理策略为无状态
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // 放行所有以"/auth/"开头的请求
                        .requestMatchers("/test/**").permitAll() // 放行所有以"/test/"开头的请求
                        .anyRequest().authenticated() // 其他所有请求都需要认证
                );
        // 在UsernamePasswordAuthenticationFilter之前添加JWT认证过滤器
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build(); // 构建并返回SecurityFilterChain对象
    }

    @Bean // 定义一个Bean，返回AuthenticationManager对象
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // 获取认证管理器
    }

    @Bean // 定义一个Bean，返回PasswordEncoder对象
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 使用BCrypt算法进行密码加密
    }
}
