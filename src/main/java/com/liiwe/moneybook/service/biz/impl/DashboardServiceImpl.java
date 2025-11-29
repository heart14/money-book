package com.liiwe.moneybook.service.biz.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liiwe.moneybook.base.bean.domain.dashboard.*;
import com.liiwe.moneybook.base.bean.domain.mb.PageListReq;
import com.liiwe.moneybook.base.bean.dto.*;
import com.liiwe.moneybook.base.bean.entity.Category;
import com.liiwe.moneybook.base.bean.entity.Transaction;
import com.liiwe.moneybook.base.common.Constants;
import com.liiwe.moneybook.mapper.CategoryMapper;
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
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lwf14
 * @since 2025/11/24 15:48
 */
@Service
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private final TransactionMapper transactionMapper;

    private final CategoryMapper categoryMapper;

    public DashboardServiceImpl(TransactionMapper transactionMapper, CategoryMapper categoryMapper) {
        this.transactionMapper = transactionMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<MonthlyIncome> getMonthlyIncome(String year) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        // 默认查当年数据
        String currentYear = StrUtil.isEmpty(year) ? String.valueOf(DateUtil.year(new Date())) : year;
        List<MonthlyTotalAmountDto> dtoList = transactionMapper.statMonthlyTotalAmount(name, currentYear, Constants.TransType.INCOME);
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
    public List<MonthlyExpense> getMonthlyExpense(String year) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        // 默认查当年数据
        String currentYear = StrUtil.isEmpty(year) ? String.valueOf(DateUtil.year(new Date())) : year;
        List<MonthlyTotalAmountDto> dtoList = transactionMapper.statMonthlyTotalAmount(name, currentYear, Constants.TransType.EXPENSE);
        List<MonthlyExpense> monthlyExpenseList = new ArrayList<>();
        if (dtoList != null) {
            for (MonthlyTotalAmountDto dto : dtoList) {
                MonthlyExpense monthlyExpense = new MonthlyExpense();
                monthlyExpense.setMonth(dto.getMonth());
                monthlyExpense.setTotalExpense(dto.getTotalAmount());
                monthlyExpenseList.add(monthlyExpense);
            }
        }
        return monthlyExpenseList;
    }

    @Override
    public List<StatCard> getStatCardData(String year) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        // 默认查当年数据
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

    @Override
    public List<CategoryIncome> getCategoryIncome(String year) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        // 默认查当年数据
        String currentYear = StrUtil.isEmpty(year) ? String.valueOf(DateUtil.year(new Date())) : year;
        List<CategoryIncomeDto> dtoList = transactionMapper.statIncomeByCategory(name, currentYear);

        List<CategoryIncome> list = new ArrayList<>();
        for (CategoryIncomeDto dto : dtoList) {
            CategoryIncome categoryIncome = new CategoryIncome(dto.getCategoryName(), dto.getTotalIncome());
            list.add(categoryIncome);
        }
        return list;
    }

    @Override
    public List<CategoryExpense> getCategoryExpense(String year) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        // 默认查当年数据
        String currentYear = StrUtil.isEmpty(year) ? String.valueOf(DateUtil.year(new Date())) : year;
        List<CategoryExpenseDto> dtoList = transactionMapper.statExpenseByCategory(name, currentYear);

        List<CategoryExpense> list = new ArrayList<>();
        for (CategoryExpenseDto dto : dtoList) {
            CategoryExpense categoryExpense = new CategoryExpense(dto.getCategoryName(), dto.getTotalExpense(), dto.getCount());
            list.add(categoryExpense);
        }
        return list;
    }

    @Override
    public List<LargeConsume> getLargeConsume(String year) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        // 默认查当年数据
        String currentYear = StrUtil.isEmpty(year) ? String.valueOf(DateUtil.year(new Date())) : year;
        List<LargeConsumeDto> dtoList = transactionMapper.selectLargeConsume(name, currentYear, Constants.DecimalNumber.THOUSAND);

        List<LargeConsume> list = new ArrayList<>();
        for (LargeConsumeDto dto : dtoList) {
            LargeConsume largeConsume = new LargeConsume(DateUtil.format(dto.getTransTime(), "yyyy.MM.dd"), dto.getTitle(), "￥" + dto.getAmount());
            list.add(largeConsume);
        }
        return list;
    }

    @Override
    public Page<TransDetail> getTransDetailPageList(PageListReq req) {
        // 获取当前登录用户的用户名
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        Page<Transaction> page = new Page<>(req.getCurrent(), req.getSize());
        LambdaQueryWrapper<Transaction> wrapper = new LambdaQueryWrapper<>();

        if (req.getCid() != null && req.getCid() != -1) {
            wrapper.eq(Transaction::getCid, req.getCid());
        }

        // mybatis-plus gt:大于, lt:小于, ge:大于等于, le:小于等于
        if (StrUtil.isNotBlank(req.getDateRangeStart())) {
            wrapper.ge(Transaction::getTransTime, DateUtil.parse(req.getDateRangeStart() + " 00:00:00"));
        }
        if (StrUtil.isNotBlank(req.getDateRangeEnd())) {
            wrapper.le(Transaction::getTransTime, DateUtil.parse(req.getDateRangeEnd() + " 23:59:59"));
        }

        if (StrUtil.isNotBlank(req.getTitle())) {
            wrapper.like(Transaction::getTitle, req.getTitle());
        }
        if (StrUtil.isNotBlank(req.getType())) {
            wrapper.eq(Transaction::getType, req.getType());
        }
        if (StrUtil.isNotBlank(req.getRemark())) {
            wrapper.like(Transaction::getRemark, req.getRemark());
        }

        wrapper.eq(Transaction::getUsername, name);
        wrapper.orderByDesc(Transaction::getTransTime);

        Page<Transaction> selectPage = transactionMapper.selectPage(page, wrapper);

        List<Transaction> records = selectPage.getRecords();

        // 查询所有分类信息
        LambdaQueryWrapper<Category> categoryQueryWrapper = new LambdaQueryWrapper<>();
        categoryQueryWrapper.eq(Category::getLevel, 2);
        categoryQueryWrapper.eq(Category::getIsDeleted, 0);

        List<Category> categoryList = categoryMapper.selectList(categoryQueryWrapper);
        Map<Long, String> nameMap = categoryList.stream().collect(Collectors.toMap(Category::getId, Category::getName));
        Map<Long, String> pathMap = categoryList.stream().collect(Collectors.toMap(Category::getId, Category::getPath));

        List<TransDetail> list = new ArrayList<>();
        for (Transaction record : records) {
            TransDetail detail = new TransDetail();
            detail.setId(record.getId());
            detail.setTitle(record.getTitle());
            detail.setAmount(record.getAmount());
            detail.setType(record.getType());
            detail.setCid(record.getCid());
            detail.setCategoryName(nameMap.get(record.getCid()));
            detail.setCategoryPath(pathMap.get(record.getCid()));
            detail.setUsername(record.getUsername());
            detail.setRemark(record.getRemark());
            detail.setTransTime(record.getTransTime());
            detail.setRecordTime(record.getRecordTime());

            list.add(detail);
        }

        Page<TransDetail> detailPage = new Page<>();
        detailPage.setRecords(list);
        detailPage.setTotal(selectPage.getTotal());
        detailPage.setSize(selectPage.getSize());
        detailPage.setCurrent(selectPage.getCurrent());
//        detailPage.setOrders(selectPage.getOr);
//        detailPage.setOptimizeCountSql(selectPage.getO)
//        detailPage.setSearchCount(selectPage.getSe)
//        detailPage.setOptimizeJoinOfCountSql(selectPage.getOp);
//        detailPage.setMaxLimit(selectPage.getM());
//        detailPage.setCountId(selectPage.get);
        return detailPage;
    }

    @Override
    public void saveOrEditTransDetail(TransDetail detail) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if (detail.getId() == null) {
            // 新增
            Transaction transaction = new Transaction();
            transaction.setTitle(detail.getTitle());
            transaction.setAmount(detail.getAmount());
            transaction.setType(detail.getType());
            transaction.setCid(detail.getCid());
            transaction.setUsername(name);
            transaction.setRemark(detail.getRemark());
            transaction.setTransTime(detail.getTransTime());
            transaction.setRecordTime(detail.getRecordTime());
            transaction.setCreateAt(new Date());
            transactionMapper.insert(transaction);
            return;
        }
        Transaction selected = transactionMapper.selectById(detail.getId());
        selected.setTitle(detail.getTitle());
        selected.setType(detail.getType());
        selected.setCid(detail.getCid());
        selected.setRemark(detail.getRemark());
        selected.setTransTime(detail.getTransTime());
        selected.setRecordTime(detail.getRecordTime());
        selected.setUpdateAt(new Date());
        transactionMapper.updateById(selected);
    }

    @Override
    public List<TabulateDto> getTabulateList(String year) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        // 默认查当年数据
        String currentYear = StrUtil.isEmpty(year) ? String.valueOf(DateUtil.year(new Date())) : year;
        List<TabulateDto> dtoList = transactionMapper.statTabulateData(name, currentYear);
        log.info("tabulate result: {}", dtoList);
        return dtoList;
    }

    /**
     * 计算百分比
     *
     * @param current 当前值
     * @param last    去年值
     * @return 增长百分比
     */
    private String percent(BigDecimal current, BigDecimal last) {
        BigDecimal subtract = current.subtract(last);
        BigDecimal divide = subtract.divide(last, 2, RoundingMode.HALF_UP);
        BigDecimal multiply = divide.multiply(Constants.DecimalNumber.HUNDRED);
        int compare = multiply.compareTo(Constants.DecimalNumber.ZERO);
        if (compare >= 0) {
            return "+" + multiply + "%";
        } else {
            return "-" + multiply + "%";
        }
    }
}
