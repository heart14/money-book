package com.liiwe.moneybook.base.bean.domain.mb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author wfli
 * @since 2025/6/13 13:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalAmountResp {

    /**
     * 总支出
     */
    private BigDecimal totalExpense;

    /**
     * 总收入
     */
    private BigDecimal totalIncome;
}
