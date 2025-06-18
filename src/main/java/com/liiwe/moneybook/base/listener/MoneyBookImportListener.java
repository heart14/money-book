package com.liiwe.moneybook.base.listener;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.liiwe.moneybook.base.bean.entity.DiaryBook;
import com.liiwe.moneybook.base.bean.entity.MoneyBook;
import com.liiwe.moneybook.base.bean.entity.SysCategory;
import com.liiwe.moneybook.base.bean.model.MoneyBookImportTemplate;
import com.liiwe.moneybook.mapper.SysCategoryMapper;
import com.liiwe.moneybook.service.SysCategoryService;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wfli
 * @since 2024/10/15 16:22
 */
@Slf4j
public class MoneyBookImportListener extends AnalysisEventListener<MoneyBookImportTemplate> {

    private final SysCategoryService categoryService;

    private final List<MoneyBookImportTemplate> records = new ArrayList<>();

    public MoneyBookImportListener(SysCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void invoke(MoneyBookImportTemplate moneyBookImportTemplate, AnalysisContext analysisContext) {
        log.info("读取.. {}", moneyBookImportTemplate);
        records.add(moneyBookImportTemplate);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("读取完成");
    }

    public List<DiaryBook> getDiaryBookList(String username) {
        List<DiaryBook> list = new ArrayList<>();
        for (MoneyBookImportTemplate record : records) {
            if (StrUtil.isNotBlank(record.getDiary())) {
                DiaryBook diaryBook = new DiaryBook();
                diaryBook.setDate(record.getDate().replace(".", "-"));
                diaryBook.setDiary(record.getDiary());
                diaryBook.setUsername(username);
                list.add(diaryBook);
            }
        }
        return list;
    }

    public List<MoneyBook> getMoneyBookList(String username) {
        // 创建一个空的列表来存储处理后的数据
        List<MoneyBook> list = new ArrayList<>();

        // 创建一个临时对象来存储上一条有日期的记录
        MoneyBookImportTemplate lastRecord = null;
        for (MoneyBookImportTemplate record : records) {
            // 如果当前记录的日期不为空，则更新 lastRecord 为当前记录
            if (StrUtil.isNotBlank(record.getDate())) {
                lastRecord = record;
            }

            if (StrUtil.isBlank(record.getAmount())){
                continue;
            }

            MoneyBook moneyBook = new MoneyBook();
            // 如果当前记录的日期为空，说明这是合并单元格的记录，与 lastRecord 是同一天
            if (StrUtil.isBlank(record.getDate())&&lastRecord!=null) {
                moneyBook.setDate(lastRecord.getDate().replace(".", "-"));
            }else {
                moneyBook.setDate(record.getDate().replace(".", "-"));
            }
            moneyBook.setTitle(record.getTitle());
            moneyBook.setType("支出");// todo 模板导入时，需要写死该类型
            moneyBook.setCategory(transferCategory(record.getCategory()));
            moneyBook.setAmount(record.getAmount());
            moneyBook.setStoredAmount(new BigDecimal(record.getAmount()).multiply(new BigDecimal("100")).longValue());
            moneyBook.setRemark(record.getRemark());

            moneyBook.setCreateTime(new Date());
            moneyBook.setRecordTime(DateUtil.parse(moneyBook.getDate() + " 23:59:59"));
            moneyBook.setUsername(username);

            list.add(moneyBook);
        }
        return list;
    }


    private int transferCategory(String cateName) {
        // excel模板导入时，填写的是分类名，存储时是分类id
        List<SysCategory> sysCategoryList = categoryService.getLeafCategoryList();
        Map<String, Integer> categoryMap = sysCategoryList.stream()
                .collect(Collectors.toMap(SysCategory::getName, SysCategory::getId));
        return categoryMap.getOrDefault(cateName, -1);
    }

}
