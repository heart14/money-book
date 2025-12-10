package com.liiwe.moneybook.service.biz.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liiwe.moneybook.base.bean.domain.auth.RegisterReq;
import com.liiwe.moneybook.base.bean.entity.SysUser;
import com.liiwe.moneybook.mapper.SysUserMapper;
import com.liiwe.moneybook.service.biz.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public void register(RegisterReq req) {

        // 根据username，检查用户是否已存在
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, req.getUsername());
        SysUser selected = userMapper.selectOne(wrapper);
        if (selected != null) {
            throw new RuntimeException("用户已存在");
        }

        // 使用Spring security推荐的BCryptPasswordEncoder加密密码
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String enc = encoder.encode(req.getPassword());
        // log.info("BCrypt password :{}", enc);
        // 新增用户
        SysUser sysUser = new SysUser(req.getUsername(), enc,req.getNickname());
        int insert = userMapper.insert(sysUser);

        if (insert != 1) {
            throw new RuntimeException("用户注册失败");
        }
    }
}
