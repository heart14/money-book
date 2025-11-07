package com.liiwe.moneybook.base.listener;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.liiwe.moneybook.base.bean.entity.DiaryBook;
import com.liiwe.moneybook.base.bean.entity.MoneyBook;
import com.liiwe.moneybook.base.bean.entity.SysCategory;
import com.liiwe.moneybook.base.bean.model.MoneyBookImportTemplate;
import com.liiwe.moneybook.base.utils.CategoryMappingUtil;
import com.liiwe.moneybook.mapper.SysCategoryMapper;
import com.liiwe.moneybook.service.SysCategoryService;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wfli
 * @since 2024/10/15 16:22
 */
@Slf4j
public class MoneyBookImportListener extends AnalysisEventListener<MoneyBookImportTemplate> {

    private final CategoryMappingUtil categoryMappingUtil;

    private final List<MoneyBookImportTemplate> records = new ArrayList<>();

    public MoneyBookImportListener(CategoryMappingUtil categoryMappingUtil) {
        this.categoryMappingUtil = categoryMappingUtil;
    }

    @Override
    public void invoke(MoneyBookImportTemplate moneyBookImportTemplate, AnalysisContext analysisContext) {
        log.info("读取.. {}", moneyBookImportTemplate);
        records.add(moneyBookImportTemplate);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("读取完成");
    }

    public List<DiaryBook> getDiaryBookList(String username) {
        List<DiaryBook> list = new ArrayList<>();
        for (MoneyBookImportTemplate record : records) {
            if (StrUtil.isNotBlank(record.getDiary())) {
                DiaryBook diaryBook = new DiaryBook();
                diaryBook.setDate(record.getDate().replace(".", "-"));
                diaryBook.setDiary(record.getDiary());
                diaryBook.setUsername(username);
                list.add(diaryBook);
            }
        }
        return list;
    }

    public List<MoneyBook> getMoneyBookList(String username) {
        // 创建一个空的列表来存储处理后的数据
        List<MoneyBook> list = new ArrayList<>();

        // 创建一个临时对象来存储上一条有日期的记录
        MoneyBookImportTemplate lastRecord = null;
        for (MoneyBookImportTemplate record : records) {
            // 如果当前记录的日期不为空，则更新 lastRecord 为当前记录
            if (StrUtil.isNotBlank(record.getDate())) {
                lastRecord = record;
            }

            if (StrUtil.isBlank(record.getAmount())){
                continue;
            }

            MoneyBook moneyBook = new MoneyBook();
            // 如果当前记录的日期为空，说明这是合并单元格的记录，与 lastRecord 是同一天
            if (StrUtil.isBlank(record.getDate())&&lastRecord!=null) {
                moneyBook.setDate(lastRecord.getDate().replace(".", "-"));
            }else {
                moneyBook.setDate(record.getDate().replace(".", "-"));
            }
            moneyBook.setTitle(record.getTitle());
            moneyBook.setType("支出");// todo 模板导入时，需要写死该类型
            moneyBook.setCategory(categoryMappingUtil.getCategoryId(record.getCategory()));
            moneyBook.setAmount(record.getAmount());
            moneyBook.setStoredAmount(new BigDecimal(record.getAmount()).multiply(new BigDecimal("100")).longValue());
            moneyBook.setRemark(record.getRemark());

            moneyBook.setCreateTime(new Date());
            moneyBook.setRecordTime(DateUtil.parse(moneyBook.getDate() + " 23:59:59"));
            moneyBook.setUsername(username);

            list.add(moneyBook);
        }
        return list;
    }


//    private int transferCategory(String cateName) {
//        // 分类名映射
//        Map<String, String> mapping = new HashMap<>();
//        // 新分类
//        mapping.put("水电燃气","生活缴费");
//        mapping.put("网络通讯","生活缴费");
//        mapping.put("烟酒茶叶","烟酒茶叶");
//        mapping.put("生鲜食材","生鲜食材");
//        mapping.put("餐饮外卖","餐饮美食");
//        mapping.put("零食饮料","餐饮美食");
//        mapping.put("公共交通","公共交通");
//        mapping.put("车辆养护","汽车养护");
//        mapping.put("车险车贷","汽车养护");
//        mapping.put("医疗器材","医疗保健");
//        mapping.put("药品","医疗保健");
//        mapping.put("保健品","医疗保健");
//        mapping.put("医疗","医疗保健");
//        mapping.put("美妆护肤","美妆护理");
//        mapping.put("个人护理","美妆护理");
//        mapping.put("服饰鞋帽","服饰装扮");
//        mapping.put("数码产品","数码潮玩");
//        mapping.put("数码周边","数码潮玩");
//        mapping.put("家具家电","未分类");
//        mapping.put("家居装饰","未分类");
//        mapping.put("日用消耗品","日用百货");
//        mapping.put("社交聚餐","未分类");
//        mapping.put("节日赠礼","未分类");
//        mapping.put("红包转账","红包转账");
//        mapping.put("房屋维修","未分类");
//        mapping.put("罚款赔偿","未分类");
//        mapping.put("电玩游戏","数码潮玩");
//        mapping.put("模型收藏","数码潮玩");
//        mapping.put("会员订阅","付费订阅");
//        mapping.put("文娱演出","文娱演出");
//        mapping.put("运动装备","运动户外");
//        mapping.put("洗浴按摩","未分类");
//        mapping.put("户外活动","运动户外");
//        mapping.put("书籍课程","未分类");
//        mapping.put("培训技能","未分类");
//        mapping.put("考级考证","未分类");
//        mapping.put("猫咪用品","宠物消费");
//        mapping.put("玩具耗材","宠物消费");
//        mapping.put("猫粮罐头","宠物消费");
//        mapping.put("鱼苗","宠物消费");
//        mapping.put("鱼缸耗材","宠物消费");
//        mapping.put("订婚","订婚");
//        mapping.put("婚礼","婚礼");
//        mapping.put("景点门票","旅游出行");
//        mapping.put("旅行购物","旅游出行");
//        mapping.put("机票酒店","旅游出行");
//        mapping.put("住房附加支出","住房附加支出");
//        mapping.put("购房","购房");
//        mapping.put("硬装","装修");
//        mapping.put("软装","装修");
//        mapping.put("投资理财","投资理财");
//        mapping.put("其它","未分类");
//
//        // 旧分类
//        mapping.put("餐饮", "餐饮美食");
//        mapping.put("住房", "未分类");
//        mapping.put("交通", "公共交通");
//        mapping.put("旅行", "旅游出行");
//        mapping.put("服饰", "服饰装扮");
//        mapping.put("汽车", "汽车养护");
//        mapping.put("宠物", "宠物消费");
//        mapping.put("生活缴费", "生活缴费");
////        mapping.put("医疗", "餐饮美食");
//        mapping.put("教育", "未分类");
//        mapping.put("数码", "数码潮玩");
//        mapping.put("文娱", "文娱演出");
//        mapping.put("日常", "日用百货");
//        mapping.put("生活", "生鲜食材");
//        mapping.put("转账", "红包转账");
////        mapping.put("其它", "餐饮美食");
//
//        // excel模板导入时，填写的是分类名，存储时是分类id
//        List<SysCategory> sysCategoryList = categoryService.getLeafCategoryList();
//        Map<String, Integer> categoryMap = sysCategoryList.stream()
//                .collect(Collectors.toMap(SysCategory::getName, SysCategory::getId));
//
//
//        String mappingName = mapping.getOrDefault(cateName,"未分类");
//
//        return categoryMap.getOrDefault(mappingName, -1);
//    }

}
