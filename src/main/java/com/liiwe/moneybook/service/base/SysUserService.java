package com.liiwe.moneybook.service.base;

import com.liiwe.moneybook.base.bean.domain.user.UserInfo;

/**
 * @author wfli
 * @since 2025/6/9 17:25
 */
public interface SysUserService {

    UserInfo getUserInfo(String username);
}
