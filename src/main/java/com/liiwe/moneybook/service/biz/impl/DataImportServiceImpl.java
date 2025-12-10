package com.liiwe.moneybook.service.biz.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.liiwe.moneybook.base.bean.entity.Category;
import com.liiwe.moneybook.base.bean.entity.Diary;
import com.liiwe.moneybook.base.bean.entity.EventTag;
import com.liiwe.moneybook.base.bean.entity.Transaction;
import com.liiwe.moneybook.base.bean.model.MoneyBookTemplate;
import com.liiwe.moneybook.base.common.Constants;
import com.liiwe.moneybook.mapper.CategoryMapper;
import com.liiwe.moneybook.mapper.DiaryMapper;
import com.liiwe.moneybook.mapper.EventTagMapper;
import com.liiwe.moneybook.mapper.TransactionMapper;
import com.liiwe.moneybook.service.biz.DataImportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lwf14
 * @since 2025/11/24 11:05
 */
@Slf4j
@Service
public class DataImportServiceImpl implements DataImportService {

    private final TransactionMapper transactionMapper;

    private final DiaryMapper diaryMapper;

    private final CategoryMapper categoryMapper;

    private final EventTagMapper eventTagMapper;

    public DataImportServiceImpl(TransactionMapper transactionMapper, DiaryMapper diaryMapper, CategoryMapper categoryMapper, EventTagMapper eventTagMapper) {
        this.transactionMapper = transactionMapper;
        this.diaryMapper = diaryMapper;
        this.categoryMapper = categoryMapper;
        this.eventTagMapper = eventTagMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public void excel(List<MoneyBookTemplate> templateList) {
        List<Diary> diaryList = getDiaryList(templateList);
        List<Transaction> transList = getTransList(templateList);
        List<EventTag> eventList = getEventList(templateList);
        if (!diaryList.isEmpty()) {
            diaryMapper.batchInsertDiary(diaryList);
        }
        if (!transList.isEmpty()) {
            transactionMapper.batchInsertTransaction(transList);
        }
        if (!eventList.isEmpty()) {
            eventTagMapper.batchInsertEventTag(eventList);
        }
    }

    private List<EventTag> getEventList(List<MoneyBookTemplate> templateList) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        List<EventTag> list = new ArrayList<>();
        // 创建一个临时对象来存储上一条有日期的记录
        MoneyBookTemplate lastRecord = null;
        for (MoneyBookTemplate record : templateList) {
            if (StrUtil.isNotBlank(record.getEventTag())) {
                // 如果当前记录的日期不为空，则更新 lastRecord 为当前记录
                if (StrUtil.isNotBlank(record.getDate())) {
                    lastRecord = record;
                }

                EventTag event = new EventTag();
                // 如果当前记录的日期为空，说明这是合并单元格的记录，与 lastRecord 是同一天
                if (StrUtil.isBlank(record.getDate()) && lastRecord != null) {
                    event.setDate(lastRecord.getDate().replace(".", "-"));
                } else {
                    event.setDate(record.getDate().replace(".", "-"));
                }
                event.setContent(record.getEventTag());
                event.setUsername(name);
                list.add(event);
            }
        }
        return list;
    }

    private List<Diary> getDiaryList(List<MoneyBookTemplate> templateList) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Diary> list = new ArrayList<>();
        for (MoneyBookTemplate record : templateList) {
            if (StrUtil.isNotBlank(record.getDiary())) {
                Diary diary = new Diary();
                diary.setDate(record.getDate().replace(".", "-"));
                diary.setContent(record.getDiary());
                diary.setWorkShift(Constants.workShiftMapping.get(record.getWorkShift()));
                diary.setUsername(name);
                list.add(diary);
            }
        }
        return list;
    }

    private List<Transaction> getTransList(List<MoneyBookTemplate> templateList) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        // 创建一个空的列表来存储处理后的数据
        List<Transaction> list = new ArrayList<>();

        // 创建一个临时对象来存储上一条有日期的记录
        MoneyBookTemplate lastRecord = null;
        for (MoneyBookTemplate record : templateList) {
            // 如果当前记录的日期不为空，则更新 lastRecord 为当前记录
            if (StrUtil.isNotBlank(record.getDate())) {
                lastRecord = record;
            }
            // 如果金额为空，则不是实际记录
            if (StrUtil.isBlank(record.getAmount())) {
                continue;
            }

            Transaction transaction = new Transaction();
            Date date = new Date();

            transaction.setTitle(record.getTitle());
            transaction.setAmount(new BigDecimal(record.getAmount()));
            transaction.setType(Constants.TransType.EXPENSE);// 模板导入固定为支出
            transaction.setCid(getCidByName(record.getCategory()));
            transaction.setUsername(name);
            transaction.setRemark(record.getRemark());
            transaction.setRecordTime(date);
            // 如果当前记录的日期为空，说明这是合并单元格的记录，与 lastRecord 是同一天
            if (StrUtil.isBlank(record.getDate()) && lastRecord != null) {
                transaction.setTransTime(DateUtil.parse(lastRecord.getDate()));
            } else {
                transaction.setTransTime(DateUtil.parse(record.getDate()));
            }

            list.add(transaction);
        }
        return list;
    }

    private Long getCidByName(String name) {
        // excel模板导入时，填写的是分类名，存储时是分类id
        List<Category> categoryList = categoryMapper.selectList(null);
        Map<String, Long> categoryMap = categoryList.stream().collect(Collectors.toMap(Category::getName, Category::getId));
        return categoryMap.get(name);
    }
}
