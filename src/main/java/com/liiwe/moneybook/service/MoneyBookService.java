package com.liiwe.moneybook.service;

import com.liiwe.moneybook.base.bean.entity.MoneyBookRecord;
import com.liiwe.moneybook.base.bean.model.RecordExcel;
import com.liiwe.moneybook.base.bean.request.SaveRecordRequest;
import com.liiwe.moneybook.base.bean.request.QueryRecordRequest;

import java.util.List;

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
}
