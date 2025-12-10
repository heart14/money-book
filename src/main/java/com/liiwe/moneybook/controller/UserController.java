package com.liiwe.moneybook.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liiwe.moneybook.base.bean.domain.mb.PageUserReq;
import com.liiwe.moneybook.base.bean.domain.manage.UserInfo;
import com.liiwe.moneybook.base.bean.model.SysResponse;
import com.liiwe.moneybook.service.base.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/info")
    public SysResponse getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        UserInfo userInfo = userService.getUserInfo(name);
        return SysResponse.success(userInfo);
    }

    @GetMapping("/list")
    public SysResponse getUserList(PageUserReq req){
        Page<UserInfo> list = userService.getUserList(req);
        return SysResponse.success(list);
    }
}
