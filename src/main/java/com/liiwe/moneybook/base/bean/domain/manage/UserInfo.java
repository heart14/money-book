package com.liiwe.moneybook.base.bean.domain.manage;

import com.liiwe.moneybook.base.bean.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wfli
 * @since 2025/6/11 15:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private Long uid;

    private String username;

    private String nickname;

    private List<String> roles;

    public UserInfo(SysUser sysUser, List<String> roles) {
        this.uid = sysUser.getUid();
        this.username = sysUser.getUsername();
        this.nickname = sysUser.getNickname();
        this.roles = roles;
    }

}
