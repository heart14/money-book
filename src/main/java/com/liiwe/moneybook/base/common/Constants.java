package com.liiwe.moneybook.base.common;

import java.math.BigDecimal;
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

    public static class TransType {
        public static final int BOTH = 3;
        public static final int EXPENSE = 2;
        public static final int INCOME = 1;
    }

    public static class CategoryLevel {
        public static final int LEVEL_1 = 1;
        public static final int LEVEL_2 = 2;
    }

    public static class IsDeleted {
        public static final int FALSE = 0;
        public static final int TRUE = 1;
    }

    public static class DecimalNumber {
        public static final BigDecimal ZERO = new BigDecimal("0");
        public static final BigDecimal HUNDRED = new BigDecimal("100");
        public static final BigDecimal THOUSAND = new BigDecimal("1000");
    }
}
