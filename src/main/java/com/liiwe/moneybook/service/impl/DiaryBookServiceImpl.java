package com.liiwe.moneybook.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liiwe.moneybook.base.bean.entity.DiaryBookRecord;
import com.liiwe.moneybook.base.bean.request.DiaryBookRequest;
import com.liiwe.moneybook.mapper.DiaryBookMapper;
import com.liiwe.moneybook.service.DiaryBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wfli
 * @since 2024/10/25 14:29
 */
@Service
@Slf4j
public class DiaryBookServiceImpl implements DiaryBookService {

    private final DiaryBookMapper diaryBookMapper;

    public DiaryBookServiceImpl(DiaryBookMapper diaryBookMapper) {
        this.diaryBookMapper = diaryBookMapper;
    }

    @Override
    @Transactional
    public void upload(List<DiaryBookRecord> list) {
        for (DiaryBookRecord record : list) {
            diaryBookMapper.insert(record);
        }
    }

    @Override
    public DiaryBookRecord record(DiaryBookRequest request) {
        DiaryBookRecord diaryBookRecord = new DiaryBookRecord();
        diaryBookRecord.setDate(StrUtil.isBlank(request.getDate()) ? DateUtil.format(DateUtil.date(), "yyyy-MM-dd") : request.getDate());
        diaryBookRecord.setDiary(request.getDiary());
        diaryBookMapper.insert(diaryBookRecord);
        return diaryBookRecord;
    }

    @Override
    public DiaryBookRecord query(DiaryBookRequest request) {
        LambdaQueryWrapper<DiaryBookRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(request.getDate()), DiaryBookRecord::getDate, request.getDate());
        return diaryBookMapper.selectOne(wrapper);
    }
}
