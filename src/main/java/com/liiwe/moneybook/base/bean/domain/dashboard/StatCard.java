package com.liiwe.moneybook.base.bean.domain.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author lwf14
 * @since 2025/11/20 15:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatCard {

    private String des;

    private BigDecimal num;

    private String change;

}
