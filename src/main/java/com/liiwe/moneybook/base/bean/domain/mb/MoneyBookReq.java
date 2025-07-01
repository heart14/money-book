package com.liiwe.moneybook.base.bean.domain.mb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wfli
 * @since 2025/7/1 9:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyBookReq {

    /**
     * 日期，yyyy-MM-dd HH:mm:ss
     */
    private String datetime;

    private String title;

    private String amount;

    private String type;

    /**
     * 分类id，只支持叶子节点分类id
     */
    private Integer categoryId;

    private String remark;
}
