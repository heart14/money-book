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
public class PageTransReq {
    /**
     * 当前页数
     */
    private int current;
    /**
     * 每页数据
     */
    private int size;
    /**
     * 日期范围start
     */
    private String dateRangeStart;
    /**
     * 日期范围end
     */
    private String dateRangeEnd;
    /**
     * 标题
     */
    private String title;
    /**
     * 交易类型
     */
    private String type;
    /**
     * 分类id
     */
    private Long cid;
    /**
     * 备注
     */
    private String remark;
}
