package com.liiwe.moneybook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liiwe.moneybook.base.bean.domain.user.UserInfo;
import com.liiwe.moneybook.base.bean.entity.SysRole;
import com.liiwe.moneybook.base.bean.entity.SysUser;
import com.liiwe.moneybook.mapper.SysRoleMapper;
import com.liiwe.moneybook.mapper.SysUserMapper;
import com.liiwe.moneybook.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public SysUser login(String username, String password) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username).eq(SysUser::getPassword, password);

        SysUser sysUser = userMapper.selectOne(wrapper);

        if (sysUser == null) {
            throw new RuntimeException("用户名或密码错误");
        }

        return sysUser;
    }

    @Override
    public void register(String username, String password) {

        // 根据username，检查用户是否已存在
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        SysUser selected = userMapper.selectOne(wrapper);
        if (selected != null) {
            throw new RuntimeException("用户已存在");
        }

        // 新增用户
        SysUser sysUser = new SysUser(username, password);
        int insert = userMapper.insert(sysUser);

        if (insert != 1) {
            throw new RuntimeException("用户注册失败");
        }
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
}
