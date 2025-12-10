package com.liiwe.moneybook.base.bean.domain.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 分类支出数据
 *
 * @author lwf14
 * @since 2025/11/26 10:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryExpense {
    /**
     * 分类名
     */
    private String name;
    /**
     * 支出金额
     */
    private BigDecimal num;
    /**
     * 支出笔数
     */
    private int count;
}
