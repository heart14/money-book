package com.liiwe.moneybook.service.biz.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liiwe.moneybook.base.bean.entity.SysUser;
import com.liiwe.moneybook.mapper.SysUserMapper;
import com.liiwe.moneybook.service.biz.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author lwf14
 * @since 2025/11/24 11:32
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final SysUserMapper userMapper;

    public AuthServiceImpl(SysUserMapper userMapper) {
        this.userMapper = userMapper;
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
}
