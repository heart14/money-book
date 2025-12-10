package com.liiwe.moneybook.base.bean.domain.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 分类收入数据
 *
 * @author lwf14
 * @since 2025/11/25 10:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryIncome {
    /**
     * 分类名
     */
    private String name;
    /**
     * 收入金额
     */
    private BigDecimal num;
}
