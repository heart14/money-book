package com.liiwe.moneybook.service;

import com.liiwe.moneybook.base.bean.entity.SysCategory;

import java.util.List;

/**
 * @author wfli
 * @since 2025/6/18 16:51
 */
public interface SysCategoryService {

    List<SysCategory> getLeafCategoryList();
}
