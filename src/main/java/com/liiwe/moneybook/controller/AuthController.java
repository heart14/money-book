package com.liiwe.moneybook.controller;

import com.liiwe.moneybook.base.bean.domain.auth.LoginReq;
import com.liiwe.moneybook.base.bean.domain.auth.RegisterReq;
import com.liiwe.moneybook.base.bean.domain.manage.UserInfo;
import com.liiwe.moneybook.base.bean.model.SysResponse;
import com.liiwe.moneybook.base.security.JwtUtils;
import com.liiwe.moneybook.service.base.SysUserService;
import com.liiwe.moneybook.service.biz.AuthService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wfli
 * @since 2025/6/10 16:30
 */
@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final AuthService authService;

    private final JwtUtils jwtUtils;

    private final StringRedisTemplate redisTemplate;

    private final SysUserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          AuthService authService,
                          JwtUtils jwtUtils,
                          StringRedisTemplate redisTemplate,
                          SysUserService userService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.jwtUtils = jwtUtils;
        this.redisTemplate = redisTemplate;
        this.userService = userService;
    }

    @PostMapping("/login")
    public SysResponse login(@RequestBody LoginReq loginReq) {
        log.info("system login: {}", loginReq);

        // 通过spring security进行登录认证
//        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
//        } catch (AuthenticationException e) {
//            throw new RuntimeException(e.getMessage());
//        }
        // 认证完成的话，查询用户信息用于生成token
        UserInfo userInfo = userService.getUserInfo(loginReq.getUsername());

        String token = jwtUtils.generateAccessToken(String.valueOf(userInfo.getUid()), userInfo.getUsername());
        String refreshToken = jwtUtils.generateRefreshToken(String.valueOf(userInfo.getUid()), userInfo.getUsername());

        // redis存储refreshToken，缓存key规则：access_token:uid refresh_token:uid，缓存value为token的jti
        Claims accessClaims = jwtUtils.parseAccessToken(token);
        Claims refreshClaims = jwtUtils.parseRefreshToken(refreshToken);

        redisTemplate.opsForValue().set("access_token:" + userInfo.getUid(), accessClaims.getId(), JwtUtils.ACCESS_EXPIRE, TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue().set("refresh_token:" + userInfo.getUid(), refreshClaims.getId(), JwtUtils.REFRESH_EXPIRE, TimeUnit.MILLISECONDS);

        return SysResponse.success("登录成功", Map.of("token", token, "refreshToken", refreshToken));
    }

    @PostMapping("/refresh")
    public SysResponse refresh(@RequestHeader("Re-Authorization") String header) {
        String token = header.substring(7);
        Claims claims = jwtUtils.parseRefreshToken(token);
        String uid = claims.getSubject();
        String username = claims.getIssuer();

        // 生成新双 token
        String newAccess = jwtUtils.generateAccessToken(uid, username);
        String newRefresh = jwtUtils.generateRefreshToken(uid, username);

        Claims newAccessClaims = jwtUtils.parseAccessToken(newAccess);
        Claims newRefreshClaims = jwtUtils.parseRefreshToken(newRefresh);

        // 覆盖 Redis
        redisTemplate.opsForValue().set("access_token:" + uid, newAccessClaims.getId(), JwtUtils.ACCESS_EXPIRE, TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue().set("refresh_token:" + uid, newRefreshClaims.getId(), JwtUtils.REFRESH_EXPIRE, TimeUnit.MILLISECONDS);

        return SysResponse.success("刷新授权成功", Map.of("token", newAccess, "refreshToken", newRefresh));
    }

    @PostMapping("/register")
    public SysResponse register(@RequestBody RegisterReq req) {
        log.info("system register: {}", req);
        authService.register(req);
        return SysResponse.success();
    }
}
