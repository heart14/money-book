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
public class QueryRecordRequest {

    /**
     * 按年查询
     */
    private String byYear;

    /**
     * 按月查询
     */
    private String byMonth;

    /**
     * 按天查询
     */
    private String byDate;

    /**
     * 按交易类型查询
     */
    private String type;

    /**
     * 按交易分类查询
     */
    private String category;

    /**
     * 按交易用户查询
     */
    private String username;

}
