package com.liiwe.moneybook.base.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wfli
 * @since 2025/6/11 9:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_sys_role")
public class SysRole {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String role;

    private String roleDesc;
}
