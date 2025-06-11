package com.liiwe.moneybook.service;

import com.liiwe.moneybook.base.bean.entity.DiaryBook;

import java.util.List;

/**
 * @author wfli
 * @since 2025/6/11 17:18
 */
public interface DiaryBookService {

    void importDiaryBook(List<DiaryBook> list);
}
