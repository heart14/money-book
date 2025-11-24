package com.liiwe.moneybook.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liiwe.moneybook.base.bean.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wfli
 * @since 2025/6/18 16:45
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
