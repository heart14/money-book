package com.liiwe.moneybook.base.bean.domain.mb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lwf14
 * @since 2025/12/10 16:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRoleReq {
    /**
     * 当前页数
     */
    private int current;
    /**
     * 每页数据
     */
    private int size;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 角色code
     */
    private String roleCode;
    /**
     * 角色描述
     */
    private String roleDesc;
}
