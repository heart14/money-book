package com.liiwe.moneybook.base.bean.domain.dashboard;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 交易详情信息
 *
 * @author lwf14
 * @since 2025/11/27 10:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransDetail {
    /**
     * id
     */
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 金额，单位元，两位小数
     */
    private BigDecimal amount;
    /**
     * 类型，收入|支出
     */
    private Integer type;
    /**
     * 分类id，关联T_CATEGORY表
     */
    private Long cid;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 分类名称
     */
    private String categoryPath;
    /**
     * 用户username
     */
    private String username;
    /**
     * 备注
     */
    private String remark;
    /**
     * 交易时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date transTime;
    /**
     * 记录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date recordTime;
}
