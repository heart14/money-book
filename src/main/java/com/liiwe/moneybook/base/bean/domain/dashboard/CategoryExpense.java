package com.liiwe.moneybook.base.bean.domain.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author lwf14
 * @since 2025/11/26 10:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryExpense {
    private String name;
    private BigDecimal num;
    private int count;
}
