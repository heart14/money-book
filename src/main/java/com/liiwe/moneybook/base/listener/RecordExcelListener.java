package com.liiwe.moneybook.base.listener;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.liiwe.moneybook.base.bean.entity.DiaryBook;
import com.liiwe.moneybook.base.bean.model.ExcelImportTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wfli
 * @since 2024/10/15 16:22
 */
@Slf4j
public class RecordExcelListener extends AnalysisEventListener<ExcelImportTemplate> {

    private final List<ExcelImportTemplate> records = new ArrayList<>();

    @Override
    public void invoke(ExcelImportTemplate excelImportTemplate, AnalysisContext analysisContext) {
        log.info("读取.. {}", excelImportTemplate);
        records.add(excelImportTemplate);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("读取完成");
    }

    public List<ExcelImportTemplate> getData() {
        List<ExcelImportTemplate> data = new ArrayList<>();
        ExcelImportTemplate lastRecord = new ExcelImportTemplate();
        for (ExcelImportTemplate record : records) {
            if (StrUtil.isNotBlank(record.getDate())) {
                lastRecord = record;
            }
            // 如果没有日期，说明是合并单元格的记录，跟lastRecord是同一天
            if (StrUtil.isBlank(record.getDate())) {
                record.setDate(lastRecord.getDate());
//                record.setWeek(lastRecord.getWeek());
//                record.setWorkType(lastRecord.getWorkType());
            }
            // 如果没有金额，这条记录不要
            if (StrUtil.isNotBlank(record.getAmount())) {
                data.add(record);
            }
        }
        return data;
    }

    public List<DiaryBook> getDiary() {
        List<DiaryBook> list = new ArrayList<>();
        for (ExcelImportTemplate record : records) {
            if (StrUtil.isNotBlank(record.getDiary())) {
                DiaryBook diaryBook = new DiaryBook();
                diaryBook.setDate(record.getDate().replace(".", "-"));
                diaryBook.setDiary(record.getDiary());
                list.add(diaryBook);
            }
        }
        return list;
    }
}
