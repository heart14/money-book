package com.liiwe.moneybook.base.bean.domain.mb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wfli
 * @since 2025/6/17 9:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryStatisticReq {

    /**
     * 按年查询或者按月查询
     */
    private String conditionType;

    /**
     * 支出或收入
     */
    private String billType;

    private String categoryId;
}
