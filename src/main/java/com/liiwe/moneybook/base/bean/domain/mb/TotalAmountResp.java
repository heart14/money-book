package com.liiwe.moneybook.base.bean.domain.mb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String totalExpense;

    /**
     * 总收入
     */
    private String totalIncome;
}
