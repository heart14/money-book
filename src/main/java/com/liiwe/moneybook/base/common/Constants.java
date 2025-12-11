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

    /**
     * 映射班次
     */
    public static final HashMap<String, String> workShiftMapping = new HashMap<>() {
        {
            put("休息", "REST");
            put("早班", "EARLY");
            put("白班", "DAY");
            put("晚班", "NIGHT");
            put("封控", "COVID");
        }
    };

    public static class TransType {
        // 收支
        public static final int BOTH = 3;
        // 支出
        public static final int EXPENSE = 2;
        // 收入
        public static final int INCOME = 1;
    }

    public static class CategoryLevel {
        // 一级分类
        public static final int LEVEL_1 = 1;
        // 二级分类
        public static final int LEVEL_2 = 2;
    }

    public static class IsDeleted {
        // 是否删除：否
        public static final int FALSE = 0;
        // 是否删除：是
        public static final int TRUE = 1;
    }

    public static class DecimalNumber {
        // BigDecimal常量0
        public static final BigDecimal ZERO = new BigDecimal("0");
        // BigDecimal常量100
        public static final BigDecimal HUNDRED = new BigDecimal("100");
        // BigDecimal常量1000
        public static final BigDecimal THOUSAND = new BigDecimal("1000");
    }

    public static class CommonStatus {
        public static final int NO = 0;
        public static final int OK = 1;
    }

    public static class UserStatus {
        // 异常
        public static final int ABNORMAL = 0;
        // 正常
        public static final int NORMAL = 1;
        // 注销
        public static final int CANCELED = 2;
    }

    public static class OnlineStatus {
        // 离线
        public static final int OFFLINE = 0;
        // 在线
        public static final int ONLINE = 1;
    }
}
