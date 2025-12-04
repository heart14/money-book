package com.liiwe.moneybook.base.bean.domain.dashboard;

import com.liiwe.moneybook.base.bean.entity.EventTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lwf14
 * @since 2025/12/3 15:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarEvent {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 日期
     */
    private String date;
    /**
     * 结束日期
     */
    private String endDate;

    /**
     * tag事件
     */
    private String content;

    public CalendarEvent(EventTag eventTag) {
        this.id = eventTag.getId();
        this.date = eventTag.getDate();
        this.endDate = eventTag.getEndDate();
        this.content = eventTag.getContent();
    }
}
