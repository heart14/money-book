package com.liiwe.moneybook.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liiwe.moneybook.base.bean.entity.Transaction;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lwf14
 * @since 2025/11/21 14:09
 */
@Mapper
public interface TransactionMapper extends BaseMapper<Transaction> {
}
