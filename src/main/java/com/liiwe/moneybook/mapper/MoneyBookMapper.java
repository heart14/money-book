package com.liiwe.moneybook.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liiwe.moneybook.base.bean.entity.MoneyBook;
import com.liiwe.moneybook.base.bean.model.MonthlyMoneyRecord;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wfli
 * @since 2024/9/25 18:38
 */
@Mapper
public interface MoneyBookMapper extends BaseMapper<MoneyBook> {

    @MapKey("category")
    List<Map<String,Object>> selectCategoryData(@Param("date") String date,@Param("type") String type,@Param("username") String username);

    List<MonthlyMoneyRecord> selectIncomeAndExpenseByCondition(@Param("date") String date,@Param("conditionType") String conditionType,@Param("username") String username);
}
