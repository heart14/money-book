package com.liiwe.moneybook.base.bean.domain.dashboard;

import com.liiwe.moneybook.base.bean.entity.EventTag;
import lombok.Data;

/**
 * @author lwf14
 * @since 2025/12/3 15:05
 */
@Data
public class CalendarEvent {

    /**
     * 日期
     */
    private String date;

    /**
     * tag事件
     */
    private String content;

    /**
     * 页面展示效果类型（暂时用不到）
     */
    private String type;

    public CalendarEvent(EventTag eventTag) {
        this.date = eventTag.getDate();
        this.content = eventTag.getContent();
        this.type = "info";
    }
}
