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

    Map<String, String> selectAnnualDataByYear(@Param("byYear") String byYear, @Param("username") String username);

    @MapKey("date")
    List<Map<String, String>> selectAnnualDataByType(@Param("type") String type, @Param("username") String username);

    @MapKey("byMonth")
    List<Map<String, String>> selectMonthDataByYear(@Param("type") String type, @Param("byYear") String byYear, @Param("username") String username);

    @MapKey("category")
    List<Map<String, String>> selectCategoryDataByYear(@Param("type") String type, @Param("byYear") String byYear, @Param("username") String username);

    @MapKey("category")
    List<Map<String, String>> selectMonthCategoryDataByYear(@Param("type") String type, @Param("byYear") String byYear, @Param("username") String username);

}
