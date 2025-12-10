package com.liiwe.moneybook.service.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liiwe.moneybook.base.bean.domain.manage.UserInfo;
import com.liiwe.moneybook.base.bean.domain.mb.PageUserReq;

/**
 * @author wfli
 * @since 2025/6/9 17:25
 */
public interface SysUserService {

    UserInfo getUserInfo(String username);

    Page<UserInfo> getUserList(PageUserReq req);

    void saveOrEditUser(UserInfo userInfo);

    void deleteUser(Long uid);
}
