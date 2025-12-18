package com.liiwe.moneybook.service.base.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liiwe.moneybook.base.bean.domain.category.CategoryReq;
import com.liiwe.moneybook.base.bean.entity.Category;
import com.liiwe.moneybook.mapper.CategoryMapper;
import com.liiwe.moneybook.service.base.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public List<Category> getTreeList() {
        // 查询所有有效分类
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getIsDeleted, 0);
        List<Category> categories = categoryMapper.selectList(wrapper);
        // 按 parentId 分组
        Map<Long, List<Category>> parentMap = categories.stream()
                .collect(Collectors.groupingBy(
                        n -> n.getParentId() == null ? 0L : n.getParentId()));

        // 递归挂孩子
        categories.forEach(n -> n.setChildren(parentMap.getOrDefault(n.getId(), new ArrayList<>())));

        // 只要根节点（parentId = 0 或 null）
        return categories.stream()
                .filter(n -> n.getParentId() == null || n.getParentId() == 0)
                .collect(Collectors.toList());
    }
}
