package com.liiwe.moneybook.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liiwe.moneybook.base.bean.domain.dashboard.*;
import com.liiwe.moneybook.base.bean.domain.mb.PageTransReq;
import com.liiwe.moneybook.base.bean.dto.TabulateDto;
import com.liiwe.moneybook.base.bean.model.SysResponse;
import com.liiwe.moneybook.service.biz.DashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/monthlyExpense")
    public SysResponse fetchMonthlyExpense() {
        List<MonthlyExpense> monthlyExpense = dashboardService.getMonthlyExpense(null);
        return SysResponse.success(monthlyExpense);
    }

    @GetMapping("/categoryIncome")
    public SysResponse fetchCategoryIncome() {
        List<CategoryIncome> list = dashboardService.getCategoryIncome(null);
        return SysResponse.success(list);
    }

    @GetMapping("/categoryExpense")
    public SysResponse fetchCategoryExpense() {
        List<CategoryExpense> list = dashboardService.getCategoryExpense(null);
        return SysResponse.success(list);
    }

    @GetMapping("/largeConsume")
    public SysResponse fetchLargeConsume() {
        List<LargeConsume> list = dashboardService.getLargeConsume(null);
        return SysResponse.success(list);
    }

    @GetMapping("/transDetailList")
    public SysResponse fetchTransDetailList(PageTransReq req) {
        Page<TransDetail> pageList = dashboardService.getTransDetailPageList(req);
        return SysResponse.success(pageList);
    }

    @PostMapping("/transDetail")
    public SysResponse postTransDetail(@RequestBody TransDetail trans) {
        log.info("post trans: {}", trans);
        dashboardService.saveOrEditTransDetail(trans);
        return SysResponse.success();
    }

    @GetMapping("/tabulateList")
    public SysResponse fetchTabulate() {
        List<TabulateDto> list = dashboardService.getTabulateList(null);
        return SysResponse.success(list);
    }

    @GetMapping("/eventList")
    public SysResponse fetchEventList(String yearMonth) {
        List<CalendarEvent> list = dashboardService.getCalendarEventList(yearMonth);
        return SysResponse.success(list);
    }

    @PostMapping("/event")
    public SysResponse postEventTag(@RequestBody CalendarEvent event) {
        dashboardService.saveOrEditEventTag(event);
        return SysResponse.success();
    }

    @DeleteMapping("/event")
    public SysResponse deleteEventTag(Long id) {
        dashboardService.deleteEventTag(id);
        return SysResponse.success();
    }

    @GetMapping("/diaryList")
    public SysResponse fetchDiaryList(String yearMonth) {
        List<CalendarDiary> list = dashboardService.getCalendarDiaryList(yearMonth);
        return SysResponse.success(list);
    }

    @PostMapping("/diary")
    public SysResponse postDiary(@RequestBody CalendarDiary diary) {
        log.info("post diary: {}", diary);
        dashboardService.saveOrEditDiary(diary);
        return SysResponse.success();
    }
}
