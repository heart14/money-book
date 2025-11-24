package com.liiwe.moneybook.controller;

import com.liiwe.moneybook.base.bean.domain.dashboard.MonthlyIncome;
import com.liiwe.moneybook.base.bean.domain.dashboard.StatCard;
import com.liiwe.moneybook.base.bean.model.SysResponse;
import com.liiwe.moneybook.service.biz.DashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
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

        StatCard item_1 = new StatCard("总收入", new BigDecimal("100.23"), "+10.23%");
        StatCard item_2 = new StatCard("总支出", new BigDecimal("200.23"), "+22.23%");
        StatCard item_3 = new StatCard("收入笔数", new BigDecimal("3"), "+3.23%");
        StatCard item_4 = new StatCard("支出笔数", new BigDecimal("6"), "+7.23%");

        ArrayList<StatCard> list = new ArrayList<>();
        list.add(item_1);
        list.add(item_2);
        list.add(item_3);
        list.add(item_4);

        return SysResponse.success(list);
    }

    @GetMapping("/monthlyIncome")
    public SysResponse fetchMonthlyIncome(){
        List<MonthlyIncome> monthlyIncome = dashboardService.getMonthlyIncome();
        return SysResponse.success(monthlyIncome);
    }
}
