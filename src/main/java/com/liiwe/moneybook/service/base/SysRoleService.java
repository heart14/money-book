package com.liiwe.moneybook.service.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liiwe.moneybook.base.bean.domain.manage.RoleInfo;
import com.liiwe.moneybook.base.bean.domain.mb.PageRoleReq;


/**
 * @author lwf14
 * @since 2025/12/10 16:55
 */
public interface SysRoleService {

    Page<RoleInfo> getRoleList(PageRoleReq req);

    void saveOrEditRole(RoleInfo roleInfo);

    void deleteRole(String roleId);
}
