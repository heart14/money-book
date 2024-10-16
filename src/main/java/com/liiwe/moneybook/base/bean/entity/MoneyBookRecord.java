package com.liiwe.moneybook.base.bean.entity;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.liiwe.moneybook.base.bean.model.RecordExcel;
import com.liiwe.moneybook.base.bean.request.SaveRecordRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author wfli
 * @since 2024/9/25 18:37
 */
@TableName("t_money_book_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyBookRecord {

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
    private Date recordTime;

    private String username;

    /**
     * 构造方法：由记账接口入参构造MoneyBookRecord
     *
     * @param request
     */
    public MoneyBookRecord(SaveRecordRequest request) {
        this.id = IdUtil.getSnowflakeNextId();
        this.createTime = DateUtil.date();

        this.date = StrUtil.isBlank(request.getDate()) ? DateUtil.format(this.createTime, "yyyy-MM-dd") : request.getDate();
        this.title = request.getTitle();
        this.amount = request.getAmount();

        this.type = StrUtil.isBlank(request.getType()) ? "未知" : request.getType();
        this.category = StrUtil.isBlank(request.getCategory()) ? "未分类" : request.getCategory();
        this.remark = request.getRemark();
        this.recordTime = this.createTime;

        this.username = request.getUsername();
    }

    /**
     * 构造方法：由excel导入记录构造MoneyBookRecord
     *
     * @param record
     */
    public MoneyBookRecord(RecordExcel record) {
        this.id = IdUtil.getSnowflakeNextId();
        this.createTime = DateUtil.date();

        this.date = record.getDate().replace(".", "-");
        this.title = record.getTitle();
        this.amount = record.getAmount();

        this.type = "支出";
        this.category = StrUtil.isBlank(record.getCategory()) ? "未分类" : record.getCategory();
        this.remark = record.getRemark();
        this.recordTime = DateUtil.parse(this.date + " 12:00:00");

        this.username = "liwenfei";
    }
}
