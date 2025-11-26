package com.liiwe.moneybook.base.bean.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lwf14
 * @since 2025/11/26 10:42
 */
@Data
public class CategoryExpenseDto {
    private String categoryName;
    private BigDecimal totalExpense;
    private int count;
}
