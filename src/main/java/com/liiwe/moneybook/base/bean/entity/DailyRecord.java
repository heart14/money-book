package com.liiwe.moneybook.base.bean.entity;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.liiwe.moneybook.base.bean.request.DailyRecordSaveRequest;
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

    public DailyRecord(DailyRecordSaveRequest request) {
        this.id = IdUtil.getSnowflakeNextId();
        this.createTime = DateUtil.date();

        this.date = StrUtil.isBlank(request.getDate())? DateUtil.format(new Date(), "yyyy-MM-dd") : request.getDate();
        this.title = request.getTitle();
        this.amount = request.getAmount();

        this.type = StrUtil.isBlank(request.getType()) ? "未知" : request.getType();
        this.category = StrUtil.isBlank(request.getCategory()) ? "未分类" : request.getCategory();
        this.remark = request.getRemark();

        this.username = request.getUsername();
    }
}
