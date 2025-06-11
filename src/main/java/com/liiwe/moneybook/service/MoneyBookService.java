package com.liiwe.moneybook.service;

import com.liiwe.moneybook.base.bean.entity.MoneyBook;

import java.util.List;

/**
 * @author wfli
 * @since 2025/6/11 17:10
 */
public interface MoneyBookService {

    void importMoneyBook(List<MoneyBook> list);
}
