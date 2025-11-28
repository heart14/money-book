package com.liiwe.moneybook.service.base.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liiwe.moneybook.base.bean.domain.category.CategoryReq;
import com.liiwe.moneybook.base.bean.entity.Category;
import com.liiwe.moneybook.mapper.CategoryMapper;
import com.liiwe.moneybook.service.base.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lwf14
 * @since 2025/11/27 17:56
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<Category> getCategoryList(CategoryReq req) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getIsDeleted, 0);
        if (req != null && req.getType() != null) {
            wrapper.eq(Category::getType, req.getType());
        }
        if (req != null && req.getLevel() != null) {
            wrapper.eq(Category::getLevel, req.getLevel());
        }
        return categoryMapper.selectList(wrapper);
    }
}
