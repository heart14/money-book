package com.liiwe.moneybook.base.bean.domain.dashboard;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 月收入金额数据
 *
 * @author lwf14
 * @since 2025/11/24 15:49
 */
@Data
public class MonthlyIncome {
    /**
     * 月份，yyyy-MM
     */
    private String month;
    /**
     * 月总收入金额
     */
    private BigDecimal totalIncome;
}
