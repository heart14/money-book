package com.liiwe.moneybook.base.listener;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.liiwe.moneybook.base.bean.entity.DiaryBookRecord;
import com.liiwe.moneybook.base.bean.model.RecordExcel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wfli
 * @since 2024/10/15 16:22
 */
@Slf4j
public class RecordExcelListener extends AnalysisEventListener<RecordExcel> {

    private final List<RecordExcel> records = new ArrayList<>();

    @Override
    public void invoke(RecordExcel recordExcel, AnalysisContext analysisContext) {
        log.info("读取.. {}", recordExcel);
        records.add(recordExcel);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("读取完成");
    }

    public List<RecordExcel> getData() {
        List<RecordExcel> data = new ArrayList<>();
        RecordExcel lastRecord = new RecordExcel();
        for (RecordExcel record : records) {
            if (StrUtil.isNotBlank(record.getDate())) {
                lastRecord = record;
            }
            // 如果没有日期，说明是合并单元格的记录，跟lastRecord是同一天
            if (StrUtil.isBlank(record.getDate())) {
                record.setDate(lastRecord.getDate());
                record.setWeek(lastRecord.getWeek());
                record.setWorkType(lastRecord.getWorkType());
            }
            // 如果没有金额，这条记录不要
            if (StrUtil.isNotBlank(record.getAmount())) {
                data.add(record);
            }
        }
        return data;
    }

    public List<DiaryBookRecord> getDiary() {
        List<DiaryBookRecord> list = new ArrayList<>();
        for (RecordExcel record : records) {
            if (StrUtil.isNotBlank(record.getDiary())) {
                DiaryBookRecord diaryBookRecord = new DiaryBookRecord();
                diaryBookRecord.setDate(record.getDate().replace(".", "-"));
                diaryBookRecord.setDiary(record.getDiary());
                list.add(diaryBookRecord);
            }
        }
        return list;
    }
}
