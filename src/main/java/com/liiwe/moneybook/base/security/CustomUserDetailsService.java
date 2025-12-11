package com.liiwe.moneybook.base.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liiwe.moneybook.base.bean.entity.SysRole;
import com.liiwe.moneybook.base.bean.entity.SysUser;
import com.liiwe.moneybook.base.common.Constants;
import com.liiwe.moneybook.mapper.SysRoleMapper;
import com.liiwe.moneybook.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义用户详情服务类
 *
 * @author wfli
 * @since 2025/6/10 15:28
 */
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    // 注入用户映射器
    private final SysUserMapper userMapper;

    private final SysRoleMapper roleMapper;

    // 构造函数，用于依赖注入
    public CustomUserDetailsService(SysUserMapper userMapper, SysRoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    // 重写 UserDetailsService 接口的 loadUserByUsername 方法
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 创建一个 LambdaQueryWrapper 对象，用于构建查询条件
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        // 设置查询条件：用户名等于传入的 username
        wrapper.eq(SysUser::getUsername, username);

        // 使用用户映射器查询用户信息
        SysUser sysUser = userMapper.selectOne(wrapper);
        // 如果查询结果为空，抛出用户名或密码错误的异常
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        // 处理用户角色信息
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<SysRole> sysRoles = roleMapper.selectUserRoleByUid(sysUser.getUid());
        if (sysRoles == null || sysRoles.isEmpty()) {
            throw new DisabledException("用户角色无权限");
        }
        for (SysRole sysRole : sysRoles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + sysRole.getRoleCode().toUpperCase()));
        }
        log.info("user roles: {}", authorities);

        // 返回一个 Spring Security 的 User 对象，包含用户名、密码和角色列表
        return new User(sysUser.getUsername(),
                sysUser.getPassword(),
                sysUser.getStatus() == Constants.UserStatus.NORMAL,
                true,
                true,
                true,
                authorities);
    }
}
