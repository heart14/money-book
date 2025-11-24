package com.liiwe.moneybook.base.bean.domain.mb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 总收入&支出查询请求参数
 *
 * @author wfli
 * @since 2025/6/13 13:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalAmountReq {

    /**
     * 日期，支持yyyy，或者yyyy-MM，或者yyyy-MM-dd
     */
    private String date;
}
