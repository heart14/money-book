package com.liiwe.moneybook.service.biz;

import com.liiwe.moneybook.base.bean.entity.SysUser;

/**
 * @author lwf14
 * @since 2025/11/24 11:32
 */
public interface AuthService {

    SysUser login(String username, String password);

    void register(String username, String password);
}
