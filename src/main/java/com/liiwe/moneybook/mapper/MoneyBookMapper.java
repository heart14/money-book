package com.liiwe.moneybook.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liiwe.moneybook.base.bean.entity.MoneyBook;
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
    List<Map<String,Object>> selectCategoryDataByYear(@Param("year") String year,@Param("type") String type,@Param("username") String username);
}
