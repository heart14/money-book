package com.liiwe.moneybook.base.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wfli
 * @since 2024/10/25 14:25
 */
@TableName("t_diary_book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryBookRecord {

    private String date;

    private String diary;
}
