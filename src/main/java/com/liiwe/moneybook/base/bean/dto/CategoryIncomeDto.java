package com.liiwe.moneybook.base.bean.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lwf14
 * @since 2025/11/25 14:17
 */
@Data
public class CategoryIncomeDto {
    private String categoryName;
    private BigDecimal totalIncome;
}
