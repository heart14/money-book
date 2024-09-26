package com.liiwe.moneybook.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liiwe.moneybook.base.bean.entity.DailyRecord;
import com.liiwe.moneybook.base.bean.request.DailyRecordQueryRequest;
import com.liiwe.moneybook.base.bean.request.DailyRecordSaveRequest;
import com.liiwe.moneybook.mapper.DailyRecordMapper;
import com.liiwe.moneybook.service.DailyRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public DailyRecord save(DailyRecordSaveRequest request) {
        DailyRecord dailyRecord = new DailyRecord(request);
        dailyRecordMapper.insert(dailyRecord);
        return dailyRecordMapper.selectById(dailyRecord.getId());
    }

    @Override
    public List<DailyRecord> query(DailyRecordQueryRequest request) {
        LambdaQueryWrapper<DailyRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(request.getDate()), DailyRecord::getDate, request.getDate())
                .eq(StrUtil.isNotBlank(request.getType()), DailyRecord::getType, request.getType())
                .eq(StrUtil.isNotBlank(request.getCategory()), DailyRecord::getCategory, request.getCategory())
                .eq(StrUtil.isNotBlank(request.getUsername()), DailyRecord::getUsername, request.getUsername());
        return dailyRecordMapper.selectList(wrapper);
    }
}
