package com.liiwe.moneybook.service.base.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liiwe.moneybook.base.bean.domain.manage.UserInfo;
import com.liiwe.moneybook.base.bean.domain.mb.PageUserReq;
import com.liiwe.moneybook.base.bean.entity.SysRole;
import com.liiwe.moneybook.base.bean.entity.SysUser;
import com.liiwe.moneybook.base.common.Constants;
import com.liiwe.moneybook.mapper.SysRoleMapper;
import com.liiwe.moneybook.mapper.SysUserMapper;
import com.liiwe.moneybook.service.base.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
                .map(sysRole -> "R_" + sysRole.getRoleCode().toUpperCase())
                .toList();
        return new UserInfo(sysUser, roles);
    }

    @Override
    public Page<UserInfo> getUserList(PageUserReq req) {

        Page<SysUser> page = new Page<>(req.getCurrent(), req.getSize());
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        if (!StrUtil.isEmpty(req.getUsername())) {
            wrapper.like(SysUser::getUsername, req.getUsername());
        }
        if (!StrUtil.isEmpty(req.getNickname())) {
            wrapper.like(SysUser::getNickname, req.getNickname());
        }
        if (req.getStatus() != null) {
            wrapper.eq(SysUser::getStatus, req.getStatus());
        }
        Page<SysUser> selectPage = userMapper.selectPage(page, wrapper);
        List<SysUser> records = selectPage.getRecords();

        List<UserInfo> list = new ArrayList<>();
        if (records != null && !records.isEmpty()) {
            for (SysUser record : records) {
                List<SysRole> sysRoles = roleMapper.selectUserRoleByUid(record.getUid());
                List<String> roles = sysRoles.stream()
                        .map(sysRole -> "R_" + sysRole.getRoleCode().toUpperCase())
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

    @Override
    public void saveOrEditUser(UserInfo userInfo) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUid, userInfo.getUid());
        SysUser selected = userMapper.selectOne(wrapper);

        if (selected == null) {
            SysUser user = new SysUser();
            user.setUsername(userInfo.getUsername());
            user.setNickname(userInfo.getNickname());
            // 默认密码123456，BCrypt格式加密
            user.setPassword(new BCryptPasswordEncoder().encode("123456"));
            user.setCreateAt(new Date());
            user.setStatus(Constants.UserStatus.NORMAL);
            userMapper.insert(user);
            return;
        }
        // 页面编辑用户信息时不允许修改其密码
        selected.setUsername(userInfo.getUsername());
        selected.setNickname(userInfo.getNickname());
        selected.setStatus(userInfo.getStatus());
        selected.setUpdateAt(new Date());
        userMapper.updateById(selected);
    }

    @Override
    public void deleteUser(Long uid) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUid, uid);
        SysUser selected = userMapper.selectOne(wrapper);
        if (selected == null) {
            throw new RuntimeException("删除失败，用户不存在");
        }
        userMapper.deleteById(uid);
    }
}
