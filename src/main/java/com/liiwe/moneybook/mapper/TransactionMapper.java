package com.liiwe.moneybook.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liiwe.moneybook.base.bean.dto.MonthlyTotalAmountDto;
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
     * @param username 用户名
     * @param transType 交易类型
     * @return MonthlyTotalAmountDto
     */
    List<MonthlyTotalAmountDto> statMonthlyTotalAmount(@Param("username") String username, @Param("transType") int transType);
}
