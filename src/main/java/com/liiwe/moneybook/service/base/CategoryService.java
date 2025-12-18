package com.liiwe.moneybook.service.base;

import com.liiwe.moneybook.base.bean.domain.category.CategoryReq;
import com.liiwe.moneybook.base.bean.entity.Category;

import java.util.List;

/**
 * @author lwf14
 * @since 2025/11/27 17:56
 */
public interface CategoryService {

    List<Category> getCategoryList(CategoryReq req);

    List<Category> getTreeList();
}
