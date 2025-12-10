package com.liiwe.moneybook.base.bean.domain.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 日历页面-日记数据
 *
 * @author lwf14
 * @since 2025/12/4 10:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarDiary {
    /**
     * diary表主键id
     */
    private Long id;
    /**
     * 日期，yyyy-MM-dd
     */
    private String date;
    /**
     * 工作班次
     */
    private String workShift;
    /**
     * 日记内容
     */
    private String diaryContent;
}
