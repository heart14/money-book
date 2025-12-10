package com.liiwe.moneybook.base.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liiwe.moneybook.base.bean.domain.manage.RoleInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author wfli
 * @since 2025/6/11 9:13
 */
@TableName("t_sys_role")
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRole extends BaseEntity{

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String roleCode;

    private String roleName;

    private String roleDesc;

    private int status;

    public SysRole(RoleInfo roleInfo){
        this.id = roleInfo.getId();
        this.roleCode = roleInfo.getRoleCode();
        this.roleName = roleInfo.getRoleName();
        this.roleDesc = roleInfo.getRoleDesc();
        this.status = roleInfo.getStatus();
        this.setCreateAt(new Date());
    }
}
