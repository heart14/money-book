package com.liiwe.moneybook.controller;

import com.liiwe.moneybook.base.bean.entity.SysCategory;
import com.liiwe.moneybook.base.bean.model.SysResponse;
import com.liiwe.moneybook.service.SysCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wfli
 * @since 2025/6/30 14:51
 */
@RestController
@RequestMapping(("/category"))
@Slf4j
public class CategoryController {

    private final SysCategoryService categoryService;

    public CategoryController(SysCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/getLeafList")
    public SysResponse getCategoryList() {
        List<SysCategory> list = categoryService.getLeafCategoryList();
        List<Map<String, Object>> result = list.stream()
                .map(category -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("label", category.getName());
                    map.put("value", category.getId());
                    return map;
                })
                .collect(Collectors.toList());
        return SysResponse.success(result);
    }
}
