package com.liiwe.moneybook.base.bean.entity;

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

    private Long uid;

    private String username;

    private String password;

    public SysUser(String username,String password){
        this.username = username;
        this.password = password;
    }

}
