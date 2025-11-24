package com.liiwe.moneybook.base.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lwf14
 * @since 2025/11/19 16:45
 */
@TableName("T_TRANSACTION")
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction extends BaseEntity {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 用户id，关联T_SYS_USER表
     */
    private Long uid;

    /**
     * 备注
     */
    private String remark;

    /**
     * 交易时间
     */
    private Date transTime;

    /**
     * 记录时间
     */
    private Date recordTime;
}
