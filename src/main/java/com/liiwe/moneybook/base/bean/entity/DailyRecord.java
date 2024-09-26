package com.liiwe.moneybook.base.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author wfli
 * @since 2024/9/25 18:37
 */
@TableName("t_daily_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyRecord {

    private Long id;

    private String date;

    private String title;

    private String amount;

    private String type;

    private String category;

    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String username;
}
