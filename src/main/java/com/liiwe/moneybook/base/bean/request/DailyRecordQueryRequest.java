package com.liiwe.moneybook.base.bean.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wfli
 * @since 2024/9/26 09:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyRecordQueryRequest {

    private String date;

    private String type;

    private String category;

    private String username;

}
