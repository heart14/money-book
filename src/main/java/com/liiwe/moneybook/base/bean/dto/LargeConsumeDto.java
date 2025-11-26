package com.liiwe.moneybook.base.bean.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lwf14
 * @since 2025/11/26 14:29
 */
@Data
public class LargeConsumeDto {
    private String title;
    private BigDecimal amount;
    private Date transTime;
}
