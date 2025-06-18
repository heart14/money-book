package com.liiwe.moneybook.base.bean.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wfli
 * @since 2025/6/18 11:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyMoneyRecord {

    private String date;
    private long totalIncome;
    private long totalExpense;
}
