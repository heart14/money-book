package com.liiwe.moneybook.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liiwe.moneybook.base.bean.entity.MoneyBookRecord;
import com.liiwe.moneybook.base.bean.model.RecordExcel;
import com.liiwe.moneybook.base.bean.request.SaveRecordRequest;
import com.liiwe.moneybook.base.bean.request.QueryRecordRequest;
import com.liiwe.moneybook.mapper.MoneyBookMapper;
import com.liiwe.moneybook.service.MoneyBookService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wfli
 * @since 2024/9/25 18:46
 */
@Service
public class MoneyBookServiceImpl implements MoneyBookService {

    private final MoneyBookMapper moneyBookMapper;

    public MoneyBookServiceImpl(MoneyBookMapper moneyBookMapper) {
        this.moneyBookMapper = moneyBookMapper;
    }

    @Override
    public MoneyBookRecord save(SaveRecordRequest request) {
        MoneyBookRecord moneyBookRecord = new MoneyBookRecord(request);
        moneyBookMapper.insert(moneyBookRecord);
        return moneyBookMapper.selectById(moneyBookRecord.getId());
    }

    @Override
    public List<MoneyBookRecord> query(QueryRecordRequest request) {
        LambdaQueryWrapper<MoneyBookRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(StrUtil.isNotBlank(request.getByYear()), MoneyBookRecord::getDate, request.getByYear())
                .likeRight(StrUtil.isNotBlank(request.getByMonth()), MoneyBookRecord::getDate, request.getByMonth())
                .eq(StrUtil.isNotBlank(request.getByDate()), MoneyBookRecord::getDate, request.getByDate())
                .eq(StrUtil.isNotBlank(request.getType()), MoneyBookRecord::getType, request.getType())
                .eq(StrUtil.isNotBlank(request.getCategory()), MoneyBookRecord::getCategory, request.getCategory())
                .eq(StrUtil.isNotBlank(request.getUsername()), MoneyBookRecord::getUsername, request.getUsername());
        return moneyBookMapper.selectList(wrapper);
    }

    @Override
    public void upload(List<RecordExcel> records) {
        MoneyBookRecord moneyBookRecord;
        for (RecordExcel record : records) {
            moneyBookRecord = new MoneyBookRecord(record);
            moneyBookMapper.insert(moneyBookRecord);
        }
    }
}
