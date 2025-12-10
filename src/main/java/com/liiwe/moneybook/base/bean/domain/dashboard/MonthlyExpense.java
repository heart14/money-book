package com.liiwe.moneybook.base.bean.domain.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 月支出金额数据
 *
 * @author lwf14
 * @since 2025/11/25 16:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyExpense {
    /**
     * 月份，yyyy-MM
     */
    private String month;
    /**
     * 月总支出金额
     */
    private BigDecimal totalExpense;
}
