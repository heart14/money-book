package com.liiwe.moneybook.base.bean.domain.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author lwf14
 * @since 2025/11/26 14:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LargeConsume {
    private String date;
    private String title;
    private String amount;
}
