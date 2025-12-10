package com.liiwe.moneybook.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liiwe.moneybook.base.bean.domain.manage.RoleInfo;
import com.liiwe.moneybook.base.bean.domain.mb.PageRoleReq;
import com.liiwe.moneybook.base.bean.model.SysResponse;
import com.liiwe.moneybook.service.base.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author lwf14
 * @since 2025/12/10 16:54
 */
@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {

    private final SysRoleService roleService;

    public RoleController(SysRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/list")
    public SysResponse getRoleList(PageRoleReq req) {
        Page<RoleInfo> list = roleService.getRoleList(req);
        return SysResponse.success(list);
    }

    /**
     * 保存或编辑角色
     *
     * @param role 角色信息
     * @return SysResponse
     */
    @PostMapping
    public SysResponse postRole(@RequestBody RoleInfo role) {
        roleService.saveOrEditRole(role);
        return SysResponse.success();
    }

    /**
     * 禁用角色
     *
     * @param roleId 角色id
     * @return SysResponse
     */
    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPER')")
    public SysResponse deleteRole(Long roleId) {
        log.info("delete role: id={}", roleId);
        roleService.deleteRole(roleId);
        return SysResponse.success();
    }
}
