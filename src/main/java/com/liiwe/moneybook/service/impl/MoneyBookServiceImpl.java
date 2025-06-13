package com.liiwe.moneybook.service.impl;

import cn.hutool.core.math.Money;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liiwe.moneybook.base.bean.domain.mb.TotalAmountReq;
import com.liiwe.moneybook.base.bean.domain.mb.TotalAmountResp;
import com.liiwe.moneybook.base.bean.entity.MoneyBook;
import com.liiwe.moneybook.base.common.Constants;
import com.liiwe.moneybook.mapper.MoneyBookMapper;
import com.liiwe.moneybook.service.MoneyBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author wfli
 * @since 2025/6/11 17:11
 */
@Slf4j
@Service
public class MoneyBookServiceImpl implements MoneyBookService {

    private final MoneyBookMapper moneyBookMapper;

    public MoneyBookServiceImpl(MoneyBookMapper moneyBookMapper) {
        this.moneyBookMapper = moneyBookMapper;
    }

    @Override
    @Transactional
    public void importMoneyBook(List<MoneyBook> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (MoneyBook moneyBook : list) {
            moneyBookMapper.insert(moneyBook);
        }
    }

    @Override
    public TotalAmountResp getTotalAmount(TotalAmountReq totalAmountReq) {
        String date = totalAmountReq.getDate().trim();
        int length = date.length();

        LambdaQueryWrapper<MoneyBook> wrapper = new LambdaQueryWrapper<>();
        switch (length) {
            case 4, 7:
                wrapper.like(MoneyBook::getDate, date);
                break;
            case 10:
                wrapper.eq(MoneyBook::getDate, date);
                break;
            default:
                throw new IllegalArgumentException("查询参数错误");
        }
        List<MoneyBook> moneyBookList = moneyBookMapper.selectList(wrapper);

        return getTotalAmountResp(moneyBookList);
    }

    private static TotalAmountResp getTotalAmountResp(List<MoneyBook> moneyBookList) {
        BigDecimal totalExpense = BigDecimal.ZERO;
        BigDecimal totalIncome = BigDecimal.ZERO;

        for (MoneyBook moneyBook : moneyBookList) {
            BigDecimal storedAmount = BigDecimal.valueOf(moneyBook.getStoredAmount());
            String type = moneyBook.getType();
            if (type.equals(Constants.BillType.EXPENSE)) {
                totalExpense = totalExpense.add(storedAmount);
            } else {
                totalIncome = totalIncome.add(storedAmount);
            }
        }

        BigDecimal HUNDRED = BigDecimal.valueOf(100L);

        TotalAmountResp totalAmountResp = new TotalAmountResp();
        totalAmountResp.setTotalExpense(totalExpense.divide(HUNDRED, 2, RoundingMode.HALF_UP).toString());
        totalAmountResp.setTotalIncome(totalIncome.divide(HUNDRED, 2, RoundingMode.HALF_UP).toString());
        return totalAmountResp;
    }
}
