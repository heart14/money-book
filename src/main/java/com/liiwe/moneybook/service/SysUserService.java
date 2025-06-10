package com.liiwe.moneybook.service;

import com.liiwe.moneybook.base.bean.entity.SysUser;

/**
 * @author wfli
 * @since 2025/6/9 17:25
 */
public interface SysUserService {

    void createSysUser(String username, String password);

    SysUser login(String username, String password);
}
