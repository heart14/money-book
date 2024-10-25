package com.liiwe.moneybook.service;

import com.liiwe.moneybook.base.bean.entity.DiaryBookRecord;

import java.util.List;

/**
 * @author wfli
 * @since 2024/10/25 14:27
 */
public interface DiaryBookService {

    void upload(List<DiaryBookRecord> list);
}
