package com.liiwe.moneybook.service.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liiwe.moneybook.base.bean.domain.mb.PageUserReq;
import com.liiwe.moneybook.base.bean.domain.user.UserInfo;
import com.liiwe.moneybook.base.bean.entity.SysUser;

import java.util.List;

/**
 * @author wfli
 * @since 2025/6/9 17:25
 */
public interface SysUserService {

    UserInfo getUserInfo(String username);

    Page<UserInfo> getUserList(PageUserReq req);
}
