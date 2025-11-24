package com.liiwe.moneybook.base.bean.domain.mb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wfli
 * @since 2025/6/16 15:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDataReq {

    /**
     * year | month
     */
    private String conditionType;
}
