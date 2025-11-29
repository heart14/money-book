package com.liiwe.moneybook.service.biz;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liiwe.moneybook.base.bean.domain.dashboard.*;
import com.liiwe.moneybook.base.bean.domain.mb.PageListReq;
import com.liiwe.moneybook.base.bean.dto.TabulateDto;

import java.util.List;

/**
 * @author lwf14
 * @since 2025/11/24 15:47
 */
public interface DashboardService {

    List<MonthlyIncome> getMonthlyIncome(String year);

    List<MonthlyExpense> getMonthlyExpense(String year);

    List<StatCard> getStatCardData(String year);

    List<CategoryIncome> getCategoryIncome(String year);

    List<CategoryExpense> getCategoryExpense(String year);

    List<LargeConsume> getLargeConsume(String year);

    Page<TransDetail> getTransDetailPageList(PageListReq req);

    void saveOrEditTransDetail(TransDetail detail);

    List<TabulateDto> getTabulateList(String year);
}
