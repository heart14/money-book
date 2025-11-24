package com.liiwe.moneybook.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
}
