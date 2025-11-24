package com.liiwe.moneybook.base.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wfli
 * @since 2025/6/9 17:21
 */
@TableName("t_sys_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser {

    @TableId(value = "uid", type = IdType.AUTO)
    private Long uid;

    private String username;

    private String password;

    private String nickname;

    public SysUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public SysUser(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

}
