package com.liiwe.moneybook.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liiwe.moneybook.base.bean.entity.MoneyBookRecord;
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
public interface MoneyBookMapper extends BaseMapper<MoneyBookRecord> {

    @MapKey("byMonth")
    List<Map<String,String>> selectMonthDataByYear(@Param("year") String year);

    @MapKey("category")
    List<Map<String,String>> selectMonthCategoryDataByYear(@Param("year") String year);

}
