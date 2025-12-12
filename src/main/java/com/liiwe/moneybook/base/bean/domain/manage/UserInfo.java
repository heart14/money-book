package com.liiwe.moneybook.base.bean.domain.manage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liiwe.moneybook.base.bean.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt;

    private List<String> roles;

    public UserInfo(SysUser sysUser, List<String> roles) {
        this.uid = sysUser.getUid();
        this.username = sysUser.getUsername();
        this.nickname = sysUser.getNickname();
        this.status = sysUser.getStatus();
        this.createAt = sysUser.getCreateAt();
        this.roles = roles;
    }

}
