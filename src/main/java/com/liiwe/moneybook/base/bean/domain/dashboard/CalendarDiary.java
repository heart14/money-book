package com.liiwe.moneybook.base.bean.domain.dashboard;

import lombok.Data;

/**
 * @author lwf14
 * @since 2025/12/4 10:55
 */
@Data
public class CalendarDiary {

    private Long id;

    private String date;

    private String workShift;

    private String diaryContent;
}
