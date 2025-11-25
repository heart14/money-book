package com.liiwe.moneybook.base.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author lwf14
 * @since 2025/11/24 18:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearlyStatCardDto {

    private BigDecimal totalIncome;

    private BigDecimal totalExpense;

    private BigDecimal totalBoth;
}
