package com.liiwe.moneybook.base.bean.domain.mb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wfli
 * @since 2025/6/18 13:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyDataResp {

    private List<String> date;
    private List<BigDecimal> totalIncome;
    private List<BigDecimal> totalExpense;

}
