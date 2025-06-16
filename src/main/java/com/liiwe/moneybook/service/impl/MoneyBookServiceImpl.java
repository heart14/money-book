package com.liiwe.moneybook.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liiwe.moneybook.base.bean.domain.mb.BillListReq;
import com.liiwe.moneybook.base.bean.domain.mb.StatisticDataReq;
import com.liiwe.moneybook.base.bean.domain.mb.TotalAmountReq;
import com.liiwe.moneybook.base.bean.domain.mb.TotalAmountResp;
import com.liiwe.moneybook.base.bean.entity.MoneyBook;
import com.liiwe.moneybook.base.common.Constants;
import com.liiwe.moneybook.mapper.MoneyBookMapper;
import com.liiwe.moneybook.service.MoneyBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wfli
 * @since 2025/6/11 17:11
 */
@Slf4j
@Service
public class MoneyBookServiceImpl implements MoneyBookService {

    // 定义常量 100，用于金额转换
    public static final BigDecimal HUNDRED = BigDecimal.valueOf(100L);

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
        // 获取当前登录用户的用户名
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        // 获取请求中的日期，并去除前后空格
        String date = totalAmountReq.getDate().trim();
        // 获取日期字符串的长度
        int length = date.length();

        // 创建一个 LambdaQueryWrapper 对象，用于构建查询条件
        LambdaQueryWrapper<MoneyBook> wrapper = new LambdaQueryWrapper<>();
        // 设置查询条件：用户名为当前登录用户
        wrapper.eq(MoneyBook::getUsername, name);

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

        // 创建响应对象
        TotalAmountResp totalAmountResp = new TotalAmountResp();
        // 设置总支出，将金额除以 100 并保留两位小数
        totalAmountResp.setTotalExpense(totalExpense.divide(HUNDRED, 2, RoundingMode.HALF_UP));
        // 设置总收入，将金额除以 100 并保留两位小数
        totalAmountResp.setTotalIncome(totalIncome.divide(HUNDRED, 2, RoundingMode.HALF_UP));
        // 返回响应对象
        return totalAmountResp;
    }

    @Override
    public List<MoneyBook> getBillList(BillListReq billListReq) {
        // 获取当前登录用户的用户名
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        // 获取请求中的日期，并去除前后空格
        String date = billListReq.getDate().trim();
        // 获取日期字符串的长度
        int length = date.length();

        // 创建一个 LambdaQueryWrapper 对象，用于构建查询条件
        LambdaQueryWrapper<MoneyBook> wrapper = new LambdaQueryWrapper<>();
        // 设置查询条件：用户名为当前登录用户
        wrapper.eq(MoneyBook::getUsername, name);

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

        if (StrUtil.isNotBlank(billListReq.getType())) {
            wrapper.eq(MoneyBook::getType, billListReq.getType());
        }
        if (StrUtil.isNotBlank(billListReq.getCategoryId())) {
            wrapper.eq(MoneyBook::getCategory, billListReq.getCategoryId());
        }

        List<MoneyBook> moneyBookList = moneyBookMapper.selectList(wrapper);
        for (MoneyBook moneyBook : moneyBookList) {
            moneyBook.setAmount(BigDecimal.valueOf(moneyBook.getStoredAmount()).divide(HUNDRED, 2, RoundingMode.HALF_UP).toString());
        }

        return moneyBookList;
    }

    /**
     * 获取统计数据，包括总收入、总支出、收入笔数、支出笔数和结余，并计算与上一周期（年或月）的变化百分比
     *
     * @return List<Map < String, Object>> 包含统计数据及其变化百分比的列表
     */
    @Override
    public List<Map<String, Object>> getStatisticData(StatisticDataReq statisticDataReq) {
        // 获取当前登录用户的用户名
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        // 创建一个 LambdaQueryWrapper 对象，用于构建查询条件
        LambdaQueryWrapper<MoneyBook> wrapper = new LambdaQueryWrapper<>();
        // 设置查询条件：用户名为当前登录用户
        wrapper.eq(MoneyBook::getUsername, name);

        // 使用hutool工具类获取当前日期
        String today = DateUtil.today();
        String year = today.substring(0, 4);
        String month = today.substring(0, 7);

        DateTime date = DateUtil.date();
        String lastYear = DateUtil.format(DateUtil.offset(date, DateField.YEAR, -1), "yyyy");
        String lastMonth = DateUtil.format(DateUtil.offset(date, DateField.MONTH, -1), "yyyy-MM");


        log.info("year :{}", year);
        log.info("month :{}", month);

        log.info("lastYear :{}", lastYear);
        log.info("lastMonth :{}", lastMonth);

        //当前年/月数据

        if ("year".equals(statisticDataReq.getConditionType())) {
            wrapper.like(MoneyBook::getDate, year);
        }
        if ("month".equals(statisticDataReq.getConditionType())) {
            wrapper.like(MoneyBook::getDate, month);
        }

        List<MoneyBook> moneyBookList = moneyBookMapper.selectList(wrapper);
//        log.info("money book list: {}", moneyBookList);


        // 初始化变量
        BigDecimal totalExpense = BigDecimal.ZERO;
        BigDecimal totalIncome = BigDecimal.ZERO;
        int incomeCount = 0;
        int expenseCount = 0;

        // 遍历每一条记录
        for (MoneyBook moneyBook : moneyBookList) {
            BigDecimal storedAmount = BigDecimal.valueOf(moneyBook.getStoredAmount());
            if (moneyBook.getType().equals("支出")) {
                totalExpense = totalExpense.add(storedAmount);
                expenseCount++;
            } else if (moneyBook.getType().equals("收入")) {
                totalIncome = totalIncome.add(storedAmount);
                incomeCount++;
            } else {
                log.error("未知交易类型，请关注：{}", moneyBook);
            }
        }

        // 计算结余
        BigDecimal balance = totalIncome.subtract(totalExpense);


        // 上一年/月数据
        // 创建一个 LambdaQueryWrapper 对象，用于构建查询条件
        LambdaQueryWrapper<MoneyBook> lastQueryWrapper = new LambdaQueryWrapper<>();
        // 设置查询条件：用户名为当前登录用户
        lastQueryWrapper.eq(MoneyBook::getUsername, name);

        if ("year".equals(statisticDataReq.getConditionType())) {
            lastQueryWrapper.like(MoneyBook::getDate, lastYear);
        }
        if ("month".equals(statisticDataReq.getConditionType())) {
            lastQueryWrapper.like(MoneyBook::getDate, lastMonth);
        }

        List<MoneyBook> lastMoneyBookList = moneyBookMapper.selectList(lastQueryWrapper);
//        log.info("lastMoneyBookList book list: {}", moneyBookList);


        // 初始化变量
        BigDecimal lastTotalExpense = BigDecimal.ZERO;
        BigDecimal lastTotalIncome = BigDecimal.ZERO;
        int lastIncomeCount = 0;
        int lastExpenseCount = 0;

        // 遍历每一条记录
        for (MoneyBook moneyBook : lastMoneyBookList) {
            BigDecimal storedAmount = BigDecimal.valueOf(moneyBook.getStoredAmount());
            if (moneyBook.getType().equals("支出")) {
                lastTotalExpense = lastTotalExpense.add(storedAmount);
                lastExpenseCount++;
            } else if (moneyBook.getType().equals("收入")) {
                lastTotalIncome = lastTotalIncome.add(storedAmount);
                lastIncomeCount++;
            } else {
                log.error("未知交易类型，请关注：{}", moneyBook);
            }
        }

        // 计算结余
        BigDecimal lastBalance = lastTotalIncome.subtract(lastTotalExpense);


        // 计算数据变动值
        BigDecimal expenseChange = totalExpense.subtract(lastTotalExpense);
        BigDecimal incomeChange = totalIncome.subtract(lastTotalIncome);
        int expenseCountChange = expenseCount - lastExpenseCount;
        int incomeCountChange = incomeCount - lastIncomeCount;
        BigDecimal balanceChange = balance.subtract(lastBalance);


        String expensePct = (expenseChange.compareTo(BigDecimal.ZERO) > 0 ? "+" : "-") + (lastTotalExpense.compareTo(BigDecimal.ZERO) == 0 ? "100" : expenseChange.divide(lastTotalExpense, 2, RoundingMode.HALF_UP).abs().multiply(HUNDRED)) + "%";
        String incomePct = (incomeChange.compareTo(BigDecimal.ZERO) > 0 ? "+" : "-") + (lastTotalIncome.compareTo(BigDecimal.ZERO) == 0 ? "100" : incomeChange.divide(lastTotalIncome, 2, RoundingMode.HALF_UP).abs().multiply(HUNDRED)) + "%";
        String expenseCountPct = (expenseCountChange > 0 ? "+" : "") + (lastExpenseCount == 0 ? "100" : expenseCountChange / lastExpenseCount * 100) + "%";
        String incomeCountPct = (incomeCountChange > 0 ? "+" : "") + (lastIncomeCount == 0 ? "100" : incomeCountChange / lastIncomeCount * 100) + "%";
        String balancePct = (balanceChange.compareTo(BigDecimal.ZERO) > 0 ? "+" : "-") + (lastBalance.compareTo(BigDecimal.ZERO) == 0 ? "100" : balanceChange.divide(lastBalance, 2, RoundingMode.HALF_UP).abs().multiply(HUNDRED)) + "%";

        // 构建结果
        List<Map<String, Object>> result = new ArrayList<>();
        // 总收入
        addResultItem(result, "总收入", "&#xe7d9", "bg-error", incomePct, totalIncome.divide(HUNDRED, 2, RoundingMode.HALF_UP));
        // 收入笔数
        addResultItem(result, "收入笔数", "&#xe7d9", "bg-error", incomeCountPct, BigDecimal.valueOf(incomeCount));
        // 总支出
        addResultItem(result, "总支出", "&#xe70f", "bg-warning", expensePct, totalExpense.divide(HUNDRED, 2, RoundingMode.HALF_UP));
        // 支出笔数
        addResultItem(result, "支出笔数", "&#xe70f", "bg-warning", expenseCountPct, BigDecimal.valueOf(expenseCount));
        // 结余
        addResultItem(result, "结余", "", "bg-success", balancePct, balance.divide(HUNDRED, 2, RoundingMode.HALF_UP));

        return result;
    }

    private void addResultItem(List<Map<String, Object>> resultList, String label, String iconfont, String bgClass, String change, BigDecimal value) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("iconfont", iconfont);
        item.put("change", change);
        item.put("label", label);
        item.put("value", value);
        item.put("class", bgClass);
        resultList.add(item);
    }
}
