package com.liiwe.moneybook.controller;

import com.liiwe.moneybook.base.bean.domain.dashboard.CategoryIncome;
import com.liiwe.moneybook.base.bean.domain.dashboard.MonthlyIncome;
import com.liiwe.moneybook.base.bean.domain.dashboard.StatCard;
import com.liiwe.moneybook.base.bean.model.SysResponse;
import com.liiwe.moneybook.service.biz.DashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lwf14
 * @since 2025/11/20 15:57
 */
@Slf4j
@RequestMapping("/dashboard")
@RestController
public class DashboardController {

    public final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/cardListData")
    public SysResponse fetchStatCardList() {
        List<StatCard> cardList = dashboardService.getStatCardData(null);
        return SysResponse.success(cardList);
    }

    @GetMapping("/monthlyIncome")
    public SysResponse fetchMonthlyIncome() {
        List<MonthlyIncome> monthlyIncome = dashboardService.getMonthlyIncome(null);
        return SysResponse.success(monthlyIncome);
    }

    @GetMapping("/categoryIncome")
    public SysResponse fetchCategoryIncome() {
        List<CategoryIncome> list = dashboardService.getCategoryIncome(null);
        return SysResponse.success(list);
    }
}
