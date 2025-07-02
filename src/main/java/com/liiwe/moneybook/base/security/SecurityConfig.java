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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * 安全配置类，用于配置Spring Security的相关设置
 *
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
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // 配置跨域资源共享(CORS)
                .csrf(AbstractHttpConfigurer::disable) // 禁用CSRF保护
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint)) // 设置未授权访问的处理入口点
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 设置会话管理策略为无状态
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // 放行所有以"/auth/"开头的请求
                        .requestMatchers("/test/**").permitAll() // 放行所有以"/test/"开头的请求
                        .requestMatchers("/sys/screen").permitAll() // 放行手机上送的请求
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

    @Bean // 定义一个Bean，返回CorsConfiguration对象
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration(); // 创建一个新的 CorsConfiguration 对象，用于配置 CORS（跨域资源共享）策略

        configuration.setAllowedOrigins(List.of("http://localhost:3006")); // 设置允许的源（域名和端口），这里只允许来自 http://localhost:3006 的请求
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 设置允许的 HTTP 方法，包括 GET、POST、PUT、DELETE 和 OPTIONS
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type")); // 设置允许的请求头，包括 Authorization、Cache-Control 和 Content-Type
        configuration.setExposedHeaders(List.of("Authorization")); // 设置暴露的响应头，这里暴露了 Authorization 头
        configuration.setAllowCredentials(true); // 允许在请求中包含凭证（如 cookies、HTTP 认证信息等）
        configuration.setMaxAge(3600L); // 设置预检请求的有效期为 3600 秒（1 小时）

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // 创建一个新的 UrlBasedCorsConfigurationSource 对象，用于基于 URL 路径配置 CORS 策略
        source.registerCorsConfiguration("/**", configuration); // 注册 CORS 配置，应用于所有路径（"/**" 表示所有路径）

        return source; // 返回配置好的 CorsConfigurationSource 对象
    }

}
