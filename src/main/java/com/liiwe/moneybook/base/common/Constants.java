package com.liiwe.moneybook.base.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author wfli
 * @since 2024/10/16 16:34
 */
public class Constants {

    public static final List<String> categories = new ArrayList<>(List.of("餐饮", "住房", "交通", "旅行", "服饰", "汽车", "宠物", "生活缴费", "医疗", "教育", "数码", "文娱", "日用", "生活", "转账", "其它", "薪酬", "奖金"));

    /**
     * 通过excel导入时，excel分类与系统分类映射关系
     */
    public static final HashMap<String, String> categoriesMapping = new HashMap<>() {
        {
            put("生活缴费", "生活缴费");
            put("汽车", "汽车");
            put("旅行", "旅行");
            put("餐饮", "餐饮");
            put("宠物", "宠物");
            put("消费-娱乐", "文娱");
            put("消费-服饰", "服饰");
            put("消费-日用", "日用");
            put("消费-数码", "数码");
            put("消费-交通", "交通");
            put("消费-其它", "未分类");
        }
    };

}
