package com.liiwe.moneybook.base.bean.domain.dashboard;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lwf14
 * @since 2025/11/24 15:49
 */
@Data
public class MonthlyIncome {
    private String month;        // yyyy-MM
    private BigDecimal totalIncome;
}
