package com.liiwe.moneybook.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liiwe.moneybook.base.bean.entity.MoneyBookRecord;
import com.liiwe.moneybook.base.bean.model.RecordExcel;
import com.liiwe.moneybook.base.bean.request.QueryRecordRequest;
import com.liiwe.moneybook.base.bean.request.SaveRecordRequest;
import com.liiwe.moneybook.base.common.Constants;
import com.liiwe.moneybook.mapper.MoneyBookMapper;
import com.liiwe.moneybook.service.MoneyBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wfli
 * @since 2024/9/25 18:46
 */
@Service
@Slf4j
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
        if (request.getType() == null) {
            request.setType("支出");
        }
        LambdaQueryWrapper<MoneyBookRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(StrUtil.isNotBlank(request.getByYear()), MoneyBookRecord::getDate, request.getByYear())
                .likeRight(StrUtil.isNotBlank(request.getByMonth()), MoneyBookRecord::getDate, request.getByMonth())
                .eq(StrUtil.isNotBlank(request.getByDate()), MoneyBookRecord::getDate, request.getByDate())
                .like(StrUtil.isNotBlank(request.getTitle()), MoneyBookRecord::getTitle, request.getTitle())
                .eq(StrUtil.isNotBlank(request.getType()), MoneyBookRecord::getType, request.getType())
                .eq(StrUtil.isNotBlank(request.getCategory()), MoneyBookRecord::getCategory, request.getCategory())
                .eq(StrUtil.isNotBlank(request.getUsername()), MoneyBookRecord::getUsername, request.getUsername());
        return moneyBookMapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public void upload(List<RecordExcel> records) {
        MoneyBookRecord moneyBookRecord;
        for (RecordExcel record : records) {
            moneyBookRecord = new MoneyBookRecord(record);
            moneyBookMapper.insert(moneyBookRecord);
        }
    }

    @Override
    public Map<String, String> queryAnnualDataByYear(QueryRecordRequest request) {
        return moneyBookMapper.selectAnnualDataByYear(request.getByYear(), request.getUsername());
    }

    @Override
    public Map<String, List<Map<String, String>>> queryAnnualData(QueryRecordRequest request) {
        List<Map<String, String>> income = moneyBookMapper.selectAnnualDataByType("收入", request.getUsername());
        List<Map<String, String>> expenses = moneyBookMapper.selectAnnualDataByType("支出", request.getUsername());
        Map<String, List<Map<String, String>>> result = new HashMap<>();
        result.put("income", income);
        result.put("expenses", expenses);
        return result;
    }

    @Override
    public List<Map<String, String>> queryMonthDataByYear(QueryRecordRequest request) {
        return moneyBookMapper.selectMonthDataByYear("支出", request.getByYear(), request.getUsername());
    }

    @Override
    public List<Map<String, String>> queryCategoryDataByYear(QueryRecordRequest request) {
        return moneyBookMapper.selectCategoryDataByYear("支出", request.getByYear(), request.getUsername());
    }

    @Override
    public List<Map<String, String>> queryMonthCategoryDataByYear(QueryRecordRequest request) {
        return moneyBookMapper.selectMonthCategoryDataByYear("支出", request.getByYear(), request.getUsername());
    }
}
