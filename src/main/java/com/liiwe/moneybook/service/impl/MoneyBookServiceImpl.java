package com.liiwe.moneybook.service.impl;

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
        // 获取请求中的日期，并去除前后空格
        String date = totalAmountReq.getDate().trim();
        // 获取日期字符串的长度
        int length = date.length();

        // 创建一个 LambdaQueryWrapper 对象，用于构建查询条件
        LambdaQueryWrapper<MoneyBook> wrapper = new LambdaQueryWrapper<>();
        // 根据日期字符串的长度来决定查询条件
        switch (length) {
            // 如果日期长度为 4 或 7，使用模糊匹配
            case 4, 7:
                wrapper.like(MoneyBook::getDate, date);
                break;
            // 如果日期长度为 10，使用精确匹配
            case 10:
                wrapper.eq(MoneyBook::getDate, date);
                break;
            // 其他情况下抛出非法参数异常
            default:
                throw new IllegalArgumentException("查询参数错误");
        }
        // 执行查询，获取符合条件的 MoneyBook 列表
        List<MoneyBook> moneyBookList = moneyBookMapper.selectList(wrapper);

        // 调用私有方法计算总金额并返回响应对象
        return getTotalAmountResp(moneyBookList);
    }

    // 私有方法，用于计算总支出和总收入
    private static TotalAmountResp getTotalAmountResp(List<MoneyBook> moneyBookList) {
        // 初始化总支出和总收入为 0
        BigDecimal totalExpense = BigDecimal.ZERO;
        BigDecimal totalIncome = BigDecimal.ZERO;

        // 遍历 MoneyBook 列表
        for (MoneyBook moneyBook : moneyBookList) {
            // 获取当前记录的金额
            BigDecimal storedAmount = BigDecimal.valueOf(moneyBook.getStoredAmount());
            // 获取当前记录的类型
            String type = moneyBook.getType();
            // 如果类型是支出，则累加到总支出
            if (type.equals(Constants.BillType.EXPENSE)) {
                totalExpense = totalExpense.add(storedAmount);
            } else {
                // 否则累加到总收入
                totalIncome = totalIncome.add(storedAmount);
            }
        }

        // 定义常量 100，用于金额转换
        BigDecimal HUNDRED = BigDecimal.valueOf(100L);

        // 创建响应对象
        TotalAmountResp totalAmountResp = new TotalAmountResp();
        // 设置总支出，将金额除以 100 并保留两位小数
        totalAmountResp.setTotalExpense(totalExpense.divide(HUNDRED, 2, RoundingMode.HALF_UP));
        // 设置总收入，将金额除以 100 并保留两位小数
        totalAmountResp.setTotalIncome(totalIncome.divide(HUNDRED, 2, RoundingMode.HALF_UP));
        // 返回响应对象
        return totalAmountResp;
    }

}
