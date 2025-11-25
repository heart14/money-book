package com.liiwe.moneybook.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liiwe.moneybook.base.bean.dto.CategoryIncomeDto;
import com.liiwe.moneybook.base.bean.dto.MonthlyTotalAmountDto;
import com.liiwe.moneybook.base.bean.dto.YearlyStatCardDto;
import com.liiwe.moneybook.base.bean.entity.Transaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lwf14
 * @since 2025/11/21 14:09
 */
@Mapper
public interface TransactionMapper extends BaseMapper<Transaction> {

    void batchInsertTransaction(@Param("list") List<Transaction> transList);

    /**
     * 根据transType字段查询月总统计值
     *
     * @param username  用户名
     * @param transType 交易类型
     * @return MonthlyTotalAmountDto
     */
    List<MonthlyTotalAmountDto> statMonthlyTotalAmount(@Param("username") String username, @Param("year") String year, @Param("transType") int transType);

    /**
     * 根据年份查询总收入、总支出、收支共项统计值
     *
     * @param username 用户名
     * @param year     年份
     * @return YearlyStatCardDto
     */
    YearlyStatCardDto statCardData(@Param("username") String username, @Param("year") String year);

    /**
     * 根据年份、分类查询总收入值
     *
     * @param username 用户名
     * @param year     年份
     * @return CategoryIncomeDto
     */
    List<CategoryIncomeDto> statIncomeByCategory(@Param("username") String username, @Param("year") String year);
}
