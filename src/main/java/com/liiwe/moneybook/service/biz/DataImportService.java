package com.liiwe.moneybook.service.biz;

import com.liiwe.moneybook.base.bean.model.MoneyBookTemplate;

import java.util.List;

/**
 * @author lwf14
 * @since 2025/11/24 11:05
 */
public interface DataImportService {

    void excel(List<MoneyBookTemplate> templateList);
}
