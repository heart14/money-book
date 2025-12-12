package com.liiwe.moneybook.base.bean.domain.mb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lwf14
 * @since 2025/12/10 15:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageUserReq {
    /**
     * 当前页数
     */
    private int current;
    /**
     * 每页数据
     */
    private int size;
    /**
     * 用户账号
     */
    private String username;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 状态
     */
    private Integer status;
}
