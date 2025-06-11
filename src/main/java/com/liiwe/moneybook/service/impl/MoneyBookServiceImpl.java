package com.liiwe.moneybook.service.impl;

import com.liiwe.moneybook.base.bean.entity.MoneyBook;
import com.liiwe.moneybook.mapper.MoneyBookMapper;
import com.liiwe.moneybook.service.MoneyBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wfli
 * @since 2025/6/11 17:11
 */
@Slf4j
@Service
public class MoneyBookServiceImpl implements MoneyBookService {

    private final MoneyBookMapper moneyBookMapper;

    public MoneyBookServiceImpl(MoneyBookMapper moneyBookMapper) {
        this.moneyBookMapper = moneyBookMapper;
    }

    @Override
    @Transactional
    public void importMoneyBook(List<MoneyBook> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (MoneyBook moneyBook : list) {
            moneyBookMapper.insert(moneyBook);
        }
    }
}
