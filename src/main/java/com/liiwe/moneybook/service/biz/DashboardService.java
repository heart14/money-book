package com.liiwe.moneybook.service.biz;

import com.liiwe.moneybook.base.bean.domain.dashboard.MonthlyIncome;

import java.util.List;

/**
 * @author lwf14
 * @since 2025/11/24 15:47
 */
public interface DashboardService {

    List<MonthlyIncome> getMonthlyIncome();
}
