package com.liiwe.moneybook.service.impl;

import com.liiwe.moneybook.base.bean.entity.DailyRecord;
import com.liiwe.moneybook.mapper.DailyRecordMapper;
import com.liiwe.moneybook.service.DailyRecordService;
import org.springframework.stereotype.Service;

/**
 * @author wfli
 * @since 2024/9/25 18:46
 */
@Service
public class DailyRecordServiceImpl implements DailyRecordService {

    private final DailyRecordMapper dailyRecordMapper;

    public DailyRecordServiceImpl(DailyRecordMapper dailyRecordMapper) {
        this.dailyRecordMapper = dailyRecordMapper;
    }

    @Override
    public DailyRecord save(DailyRecord record) {
        dailyRecordMapper.insert(record);
        return dailyRecordMapper.selectById(record.getId());
    }
}
