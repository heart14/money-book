package com.liiwe.moneybook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liiwe.moneybook.base.bean.entity.SysCategory;
import com.liiwe.moneybook.mapper.SysCategoryMapper;
import com.liiwe.moneybook.service.SysCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wfli
 * @since 2025/6/18 16:52
 */
@Service
@Slf4j
public class SysCategoryServiceImpl implements SysCategoryService {

    private final SysCategoryMapper categoryMapper;
    public SysCategoryServiceImpl(SysCategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<SysCategory> getLeafCategoryList() {

        LambdaQueryWrapper<SysCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysCategory::getIsLeaf, 1);
        wrapper.eq(SysCategory::getIsActive, 1);

        return categoryMapper.selectList(wrapper);
    }
}
