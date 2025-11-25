package com.liiwe.moneybook.service.biz;

import com.liiwe.moneybook.base.bean.domain.dashboard.CategoryIncome;
import com.liiwe.moneybook.base.bean.domain.dashboard.MonthlyIncome;
import com.liiwe.moneybook.base.bean.domain.dashboard.StatCard;

import java.util.List;

/**
 * @author lwf14
 * @since 2025/11/24 15:47
 */
public interface DashboardService {

    List<MonthlyIncome> getMonthlyIncome(String year);

    List<StatCard> getStatCardData(String year);
}
