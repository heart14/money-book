package com.liiwe.moneybook.controller;

import com.liiwe.moneybook.base.bean.domain.auth.LoginReq;
import com.liiwe.moneybook.base.bean.entity.SysUser;
import com.liiwe.moneybook.base.bean.model.SysResponse;
import com.liiwe.moneybook.base.security.JwtUtils;
import com.liiwe.moneybook.service.biz.AuthService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wfli
 * @since 2025/6/10 16:30
 */
@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    private final JwtUtils jwtUtils;

    public AuthController(AuthService authService, JwtUtils jwtUtils) {
        this.authService = authService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public SysResponse login(@RequestBody LoginReq loginReq) {
        log.info("system login: {}", loginReq);

        SysUser sysUser = authService.login(loginReq.getUsername(), loginReq.getPassword());

        Map<String, String> data = new HashMap<>();
        data.put("token", jwtUtils.generateAccessToken(String.valueOf(sysUser.getUid()), sysUser.getUsername()));
        data.put("refreshToken", jwtUtils.generateRefreshToken(String.valueOf(sysUser.getUid()), sysUser.getUsername()));

        // redis存储refreshToken，key规则：refresh_token:uid

        return SysResponse.success("登录成功", data);
    }

    @PostMapping("/register")
    public SysResponse register(@RequestBody LoginReq loginReq) {
        log.info("system register: {}", loginReq);

        authService.register(loginReq.getUsername(), loginReq.getPassword());
        return SysResponse.success();
    }

    @GetMapping("/refresh/{refreshToken}")
    public SysResponse refreshToken(@PathVariable("refreshToken") String refreshToken) {

        // 可以解析token，说明未过期
        Claims claims = jwtUtils.parseToken(refreshToken);
        String uid = claims.getSubject();
        String username = claims.getIssuer();

        // redis查询key refresh_token:uid，将redis中存储的值与当前值进行比较
        // 如果相同，则说明refreshToken正确且在有效期内

        // 生成新的token并在redis中重新存储

        Map<String, String> data = new HashMap<>();
        data.put("token", jwtUtils.generateAccessToken(uid, username));
        data.put("refreshToken", jwtUtils.generateRefreshToken(uid, username));

        // redis存储refreshToken，key规则：refresh_token:uid

        return SysResponse.success("AccessToken刷新成功", data);
    }
}
