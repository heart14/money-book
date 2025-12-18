package com.liiwe.moneybook.controller;

import com.liiwe.moneybook.base.bean.domain.category.CategoryReq;
import com.liiwe.moneybook.base.bean.entity.Category;
import com.liiwe.moneybook.base.bean.model.SysResponse;
import com.liiwe.moneybook.service.base.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wfli
 * @since 2025/6/30 14:51
 */
@RestController
@RequestMapping(("/category"))
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public SysResponse fetchCategoryList(CategoryReq req) {
        List<Category> list = categoryService.getCategoryList(req);
        return SysResponse.success(list);
    }

    @GetMapping("/tree")
    public SysResponse fetchTreeList() {
        log.info("fetch tree list");
        List<Category> treeList = categoryService.getTreeList();
        return SysResponse.success(treeList);
    }
}
