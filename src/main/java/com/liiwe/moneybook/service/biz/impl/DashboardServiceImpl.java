package com.liiwe.moneybook.service.biz.impl;

import com.liiwe.moneybook.base.bean.domain.dashboard.MonthlyIncome;
import com.liiwe.moneybook.base.bean.dto.MonthlyTotalAmountDto;
import com.liiwe.moneybook.base.common.Constants;
import com.liiwe.moneybook.mapper.TransactionMapper;
import com.liiwe.moneybook.service.biz.DashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lwf14
 * @since 2025/11/24 15:48
 */
@Service
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private final TransactionMapper transactionMapper;

    public DashboardServiceImpl(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    @Override
    public List<MonthlyIncome> getMonthlyIncome() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        List<MonthlyTotalAmountDto> dtoList = transactionMapper.statMonthlyTotalAmount(name, Constants.TransType.INCOME);
        List<MonthlyIncome> monthlyIncomeList = new ArrayList<>();
        if (dtoList != null) {
            for (MonthlyTotalAmountDto dto : dtoList) {
                MonthlyIncome monthlyIncome = new MonthlyIncome();
                monthlyIncome.setMonth(dto.getMonth());
                monthlyIncome.setTotalIncome(dto.getTotalAmount());
                monthlyIncomeList.add(monthlyIncome);
            }
        }
        return monthlyIncomeList;
    }
}
