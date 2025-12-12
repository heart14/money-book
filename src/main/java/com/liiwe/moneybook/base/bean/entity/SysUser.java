package com.liiwe.moneybook.base.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liiwe.moneybook.base.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author wfli
 * @since 2025/6/9 17:21
 */
@TableName("t_sys_user")
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser extends BaseEntity{

    @TableId(value = "uid", type = IdType.AUTO)
    private Long uid;

    private String username;

    private String password;

    private String nickname;

    private Integer status;

    public SysUser(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.status = Constants.UserStatus.NORMAL;
        this.setCreateAt(new Date());
    }

}
