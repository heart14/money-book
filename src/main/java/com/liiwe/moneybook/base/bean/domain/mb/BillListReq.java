package com.liiwe.moneybook.base.bean.domain.mb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wfli
 * @since 2025/6/13 16:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillListReq {

    /**
     * 日期，支持yyyy，或者yyyy-MM，或者yyyy-MM-dd
     */
    private String date;

    private String type;

    /**
     * 分类id，只支持叶子节点分类id
     */
    private String categoryId;

}
