package com.liiwe.moneybook.base.bean.domain.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 大额消费数据
 *
 * @author lwf14
 * @since 2025/11/26 14:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LargeConsume {
    /**
     * 日期
     */
    private String date;
    /**
     * 标题
     */
    private String title;
    /**
     * 金额
     */
    private String amount;
}
