package com.liiwe.moneybook.base.bean.domain.manage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liiwe.moneybook.base.bean.entity.SysRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author lwf14
 * @since 2025/12/10 17:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleInfo {
    private Long id;

    private String roleCode;

    private String roleName;

    private String roleDesc;

    private int status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt;

    public RoleInfo(SysRole sysRole) {
        this.id = sysRole.getId();
        this.roleCode = sysRole.getRoleCode();
        this.roleName = sysRole.getRoleName();
        this.roleDesc = sysRole.getRoleDesc();
        this.status = sysRole.getStatus();
        this.createAt = sysRole.getCreateAt();
    }
}
