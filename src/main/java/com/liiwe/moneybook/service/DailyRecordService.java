package com.liiwe.moneybook.service;

import com.liiwe.moneybook.base.bean.entity.DailyRecord;
import com.liiwe.moneybook.base.bean.request.DailyRecordQueryRequest;

import java.util.List;

/**
 * @author wfli
 * @since 2024/9/25 18:45
 */
public interface DailyRecordService {

    DailyRecord save(DailyRecord record);

    List<DailyRecord> query(DailyRecordQueryRequest request);
}
