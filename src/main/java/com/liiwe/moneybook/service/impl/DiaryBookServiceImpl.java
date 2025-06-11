package com.liiwe.moneybook.service.impl;

import com.liiwe.moneybook.base.bean.entity.DiaryBook;
import com.liiwe.moneybook.mapper.DiaryBookMapper;
import com.liiwe.moneybook.service.DiaryBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wfli
 * @since 2025/6/11 17:19
 */
@Slf4j
@Service
public class DiaryBookServiceImpl implements DiaryBookService {

    private final DiaryBookMapper diaryBookMapper;

    public DiaryBookServiceImpl(DiaryBookMapper diaryBookMapper) {
        this.diaryBookMapper = diaryBookMapper;
    }

    @Override
    @Transactional
    public void importDiaryBook(List<DiaryBook> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (DiaryBook diaryBook : list) {
            diaryBookMapper.insert(diaryBook);
        }
    }
}
