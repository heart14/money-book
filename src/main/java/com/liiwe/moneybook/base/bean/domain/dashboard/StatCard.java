package com.liiwe.moneybook.base.bean.domain.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 统计卡片数据
 *
 * @author lwf14
 * @since 2025/11/20 15:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatCard {
    /**
     * 卡片描述
     */
    private String des;
    /**
     * 金额
     */
    private BigDecimal num;
    /**
     * 同比去年变化值
     */
    private String change;

}
