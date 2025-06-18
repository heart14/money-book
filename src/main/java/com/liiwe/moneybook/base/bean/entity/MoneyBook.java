package com.liiwe.moneybook.base.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.Transient;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * @author wfli
 * @since 2024/9/25 18:37
 */
@TableName("t_money_book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyBook {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String date;

    private String title;

    /**
     * 数据库存储金额，单位：分
     */
    private Long storedAmount;

    /**
     * 金额，单位：元，用于业务使用，该字段不存储到数据库
     */
    @TableField(exist = false)
    private String amount;

    private String type;

    private Integer category;

    @TableField(exist = false)
    private String categoryName;

    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recordTime;

    private String username;

//    public String getAmount() {
//        return BigDecimal.valueOf(this.storedAmount).divide(BigDecimal.valueOf(100L),2, RoundingMode.HALF_UP).toString();
//    }
}
