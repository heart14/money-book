package com.liiwe.moneybook.service;

import com.liiwe.moneybook.base.bean.entity.MoneyBookRecord;
import com.liiwe.moneybook.base.bean.model.RecordExcel;
import com.liiwe.moneybook.base.bean.request.SaveRecordRequest;
import com.liiwe.moneybook.base.bean.request.QueryRecordRequest;

import java.util.List;
import java.util.Map;

/**
 * @author wfli
 * @since 2024/9/25 18:45
 */
public interface MoneyBookService {

    /**
     * 保存记账记录
     * @param request
     * @return
     */
    MoneyBookRecord save(SaveRecordRequest request);

    /**
     * 查询记账记录
     * @param request
     * @return
     */
    List<MoneyBookRecord> query(QueryRecordRequest request);

    /**
     * 从excel导入记账记录
     * @param records
     */
    void upload(List<RecordExcel> records);

    /**
     * 查询年度收支数据
     * @param request
     * @return
     */
    Map<String, List<Map<String, String>>> queryAnnualDataByType(QueryRecordRequest request);

    /**
     * 查询月支出统计图数据
     * @param request
     * @return
     */
    List<Map<String, String>> queryMonthDataByYear(QueryRecordRequest request);

    /**
     * 查询分类支出统计图数据
     * @param request
     * @return
     */
    List<Map<String, String>> queryCategoryDataByYear(QueryRecordRequest request);

    /**
     * 查询月分类统计表数据
     * @param request
     * @return
     */
    List<Map<String, String>> queryMonthCategoryDataByYear(QueryRecordRequest request);
}
