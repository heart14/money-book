package com.liiwe.moneybook.base.bean.domain.mb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wfli
 * @since 2025/6/19 14:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageListReq {

    private int current;

    private int size;

    private String dateRangeStart;

    private String dateRangeEnd;

    private String title;

    private String type;

    private Long cid;

    private String remark;
}
