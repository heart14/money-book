package com.liiwe.moneybook.base.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author lwf14
 * @since 2025/11/24 15:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyTotalAmountDto {
    private String month;        // yyyy-MM
    private BigDecimal totalAmount;
}
