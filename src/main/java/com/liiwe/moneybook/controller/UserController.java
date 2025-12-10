package com.liiwe.moneybook.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liiwe.moneybook.base.bean.domain.manage.UserInfo;
import com.liiwe.moneybook.base.bean.domain.mb.PageUserReq;
import com.liiwe.moneybook.base.bean.model.SysResponse;
import com.liiwe.moneybook.service.base.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    /**
     * 获取当前登录用户信息
     *
     * @return SysResponse
     */
    @GetMapping("/info")
    public SysResponse getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        UserInfo userInfo = userService.getUserInfo(name);
        return SysResponse.success(userInfo);
    }

    /**
     * 分页查询所有用户列表
     *
     * @param req 分页查询参数
     * @return SysResponse
     */
    @GetMapping("/list")
    public SysResponse getUserList(PageUserReq req) {
        Page<UserInfo> list = userService.getUserList(req);
        return SysResponse.success(list);
    }


    /**
     * 保存或编辑用户
     *
     * @param userInfo 用户信息
     * @return SysResponse
     */
    @PostMapping
    public SysResponse postUser(@RequestBody UserInfo userInfo) {
        userService.saveOrEditUser(userInfo);
        return SysResponse.success();
    }

    /**
     * 删除用户
     *
     * @param uid 用户id
     * @return SysResponse
     */
    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPER')")
    public SysResponse deleteUser(Long uid) {
        log.info("delete user: id={}", uid);
        userService.deleteUser(uid);
        return SysResponse.success();
    }
}
