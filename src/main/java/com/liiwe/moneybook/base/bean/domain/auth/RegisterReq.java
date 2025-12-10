package com.liiwe.moneybook.base.bean.domain.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lwf14
 * @since 2025/12/10 17:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterReq {
    @NotBlank(message = "用户账号不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "用户昵称不能为空")
    private String nickname;
}
