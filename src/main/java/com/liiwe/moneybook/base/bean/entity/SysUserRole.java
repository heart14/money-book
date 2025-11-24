package com.liiwe.moneybook.base.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wfli
 * @since 2025/6/11 11:27
 */
@TableName("t_user_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRole {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long uid;

    private Long roleId;
}
