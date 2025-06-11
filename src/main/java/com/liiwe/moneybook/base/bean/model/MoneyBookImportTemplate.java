package com.liiwe.moneybook.base.bean.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wfli
 * @since 2024/10/15 16:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyBookImportTemplate {

    @ExcelProperty(value = "日期", index = 0)
    private String date;

//    @ExcelProperty(value = "星期", index = 1)
//    private String week;
//
//    @ExcelProperty(value = "班次", index = 2)
//    private String workType;

    @ExcelProperty(value = "描述", index = 3)
    private String title;

    @ExcelProperty(value = "类别", index = 4)
    private String category;

    @ExcelProperty(value = "金额", index = 5)
    private String amount;

    @ExcelProperty(value = "备注", index = 6)
    private String remark;

    @ExcelProperty(value = "日记", index = 8)
    private String diary;
}
