package com.liiwe.moneybook.service.base.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liiwe.moneybook.base.bean.domain.mb.PageUserReq;
import com.liiwe.moneybook.base.bean.domain.user.UserInfo;
import com.liiwe.moneybook.base.bean.entity.SysRole;
import com.liiwe.moneybook.base.bean.entity.SysUser;
import com.liiwe.moneybook.mapper.SysRoleMapper;
import com.liiwe.moneybook.mapper.SysUserMapper;
import com.liiwe.moneybook.service.base.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wfli
 * @since 2025/6/9 17:27
 */
@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper userMapper;

    private final SysRoleMapper roleMapper;

    public SysUserServiceImpl(SysUserMapper userMapper, SysRoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    public UserInfo getUserInfo(String username) {
        // 根据username，查询用户信息
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        SysUser sysUser = userMapper.selectOne(wrapper);
        if (sysUser == null) {
            throw new RuntimeException("用户不存在");
        }
        List<SysRole> sysRoles = roleMapper.selectUserRoleByUid(sysUser.getUid());
        List<String> roles = sysRoles.stream()
                .map(sysRole -> "R_" + sysRole.getRole().toUpperCase())
                .toList();
        return new UserInfo(sysUser, roles);
    }

    @Override
    public Page<UserInfo> getUserList(PageUserReq req) {

        Page<SysUser> page = new Page<>(req.getCurrent(), req.getSize());
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        Page<SysUser> selectPage = userMapper.selectPage(page, wrapper);
        List<SysUser> records = selectPage.getRecords();

        List<UserInfo> list = new ArrayList<>();
        if (records != null && !records.isEmpty()) {
            for (SysUser record : records) {
                List<SysRole> sysRoles = roleMapper.selectUserRoleByUid(record.getUid());
                List<String> roles = sysRoles.stream()
                        .map(sysRole -> "R_" + sysRole.getRole().toUpperCase())
                        .toList();
                UserInfo userInfo = new UserInfo(record, roles);
                list.add(userInfo);
            }
        }

        Page<UserInfo> result = new Page<>();
        result.setRecords(list);
        result.setTotal(selectPage.getTotal());
        result.setSize(selectPage.getSize());
        result.setCurrent(selectPage.getCurrent());

        return result;
    }
}
