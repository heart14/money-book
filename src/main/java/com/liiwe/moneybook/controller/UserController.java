package com.liiwe.moneybook.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.liiwe.moneybook.base.bean.domain.system.LoginReq;
import com.liiwe.moneybook.base.bean.model.SysResponse;
import com.liiwe.moneybook.service.SysUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author wfli
 * @since 2025/6/9 15:39
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final SysUserService userService;

    public UserController(SysUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public SysResponse saveSysUser(@RequestBody @Valid LoginReq loginReq) {
        userService.createSysUser(loginReq.getUserName(), loginReq.getPassword());
        return SysResponse.success("新增用户成功", null);
    }

    @GetMapping("/info")
    public SysResponse getUserInfo() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        // TODO 从请求头获取token，解析获取用户uid，然后根据uid进行查询用户信息
        String userinfo = "{\"userId\":\"1\",\"userName\":\""+name+"\",\"roles\":[\"R_SUPER\"],\"buttons\":[\"B_CODE1\",\"B_CODE2\",\"B_CODE3\"]}";

        JSONObject json = JSONUtil.parseObj(userinfo);
        return SysResponse.success(json);
    }
}
