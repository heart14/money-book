package com.liiwe.moneybook.base.bean.domain.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author lwf14
 * @since 2025/11/25 16:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyExpense {
    private String month;        // yyyy-MM
    private BigDecimal totalExpense;
}
