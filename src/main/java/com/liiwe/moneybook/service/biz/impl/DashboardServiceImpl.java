package com.liiwe.moneybook.service.biz.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.liiwe.moneybook.base.bean.domain.dashboard.MonthlyIncome;
import com.liiwe.moneybook.base.bean.domain.dashboard.StatCard;
import com.liiwe.moneybook.base.bean.dto.MonthlyTotalAmountDto;
import com.liiwe.moneybook.base.bean.dto.YearlyStatCardDto;
import com.liiwe.moneybook.base.common.Constants;
import com.liiwe.moneybook.mapper.TransactionMapper;
import com.liiwe.moneybook.service.biz.DashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public List<StatCard> getStatCardData(String year) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String currentYear = StrUtil.isEmpty(year) ? String.valueOf(DateUtil.year(new Date())) : year;
        YearlyStatCardDto dto = transactionMapper.statCardData(name, currentYear);

        DateTime offset = DateUtil.parse(currentYear + "0101").offset(DateField.YEAR, -1);
        String lastYear = String.valueOf(offset.getField(DateField.YEAR));

        YearlyStatCardDto lastDto = transactionMapper.statCardData(name, lastYear);

        StatCard card1 = new StatCard("总收入", dto.getTotalIncome(), (lastDto == null) ? "+100%" : percent(dto.getTotalIncome(), lastDto.getTotalIncome()));
        StatCard card2 = new StatCard("总支出", dto.getTotalExpense(), (lastDto == null) ? "+100%" : percent(dto.getTotalExpense(), lastDto.getTotalExpense()));
        StatCard card3 = new StatCard("总结余", dto.getTotalIncome().subtract(dto.getTotalExpense()), (lastDto == null) ? "+100%" : percent(dto.getTotalIncome().subtract(dto.getTotalExpense()), lastDto.getTotalIncome().subtract(lastDto.getTotalExpense())));
        StatCard card4 = new StatCard("总收支共项", dto.getTotalBoth(), (lastDto == null) ? "+100%" : percent(dto.getTotalBoth(), lastDto.getTotalBoth()));

        List<StatCard> list = new ArrayList<>();
        list.add(card1);
        list.add(card2);
        list.add(card3);
        list.add(card4);
        return list;
    }


    private String percent(BigDecimal current, BigDecimal last) {
        BigDecimal subtract = current.subtract(last);
        BigDecimal divide = subtract.divide(last, 2, RoundingMode.HALF_UP);
        BigDecimal multiply = divide.multiply(new BigDecimal("100"));
        int compare = multiply.compareTo(new BigDecimal("0"));
        if (compare >= 0) {
            return "+" + multiply + "%";
        } else {
            return "-" + multiply + "%";
        }
    }
}
