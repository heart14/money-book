//package com.liiwe.moneybook.controller;
//
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.liiwe.moneybook.base.bean.domain.mb.*;
//import com.liiwe.moneybook.base.bean.entity.MoneyBook;
//import com.liiwe.moneybook.base.bean.model.SysResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
/// **
// * @author wfli
// * @since 2024/9/25 18:44
// */
//@RestController
//@RequestMapping(("/moneybook"))
//@Slf4j
//public class MoneyBookController {
//
////    private final MoneyBookService moneyBookService;
//
//    public MoneyBookController(MoneyBookService moneyBookService) {
//        this.moneyBookService = moneyBookService;
//    }
//
//    /**
//     * 分页查询记账数据
//     *
//     * @param
//     */
//    @GetMapping("/getPageList")
//    public SysResponse getPageList(PageListReq req) {
//        log.info("getPageList: {}", req);
//        Page<MoneyBook> pageList = moneyBookService.getPageList(req);
//        log.info("getPageList response: {}", pageList);
//
//        return SysResponse.success(pageList);
//    }
//
//
//    /**
//     * 按日期查询总支出与收入数据
//     *
//     * @param req yyyy | yyyy-MM | yyyy-MM-dd
//     */
//    @GetMapping("/getTotalAmount")
//    public SysResponse getTotalAmount(TotalAmountReq req) {
//        log.info("getTotalAmount: {}", req);
//        TotalAmountResp totalAmount = moneyBookService.getTotalAmount(req);
//        log.info("getTotalAmount response: {}", totalAmount);
//        return SysResponse.success(totalAmount);
//    }
//
//
//    /**
//     * 按日期查询账单记录集合
//     */
//    @GetMapping("/getBillList")
//    public SysResponse getBillList(BillListReq req) {
//        log.info("getBillList: {}", req);
//        List<MoneyBook> billList = moneyBookService.getBillList(req);
//        log.info("getBillList response: {}", billList);
//        return SysResponse.success(billList);
//    }
//
//    /**
//     * 按年或月，获取统计数据
//     * 包括总收入、总支出、收入笔数、支出笔数和结余，并计算与上一周期（年或月）的变化百分比
//     *
//     * @param req year | month
//     */
//    @GetMapping("/getStatisticData")
//    public SysResponse getStatisticData(StatisticDataReq req) {
//        log.info("getStatisticData: {}", req);
//
//        List<Map<String, Object>> result = moneyBookService.getStatisticData(req);
//        log.info("getStatisticData response: {}", result);
//        return SysResponse.success(result);
//    }
//
//    @GetMapping("/getCategoryStatistic")
//    public SysResponse getCategoryStatistic(CategoryStatisticReq req) {
//        log.info("getCategoryStatistic: {}", req);
//
//        List<Map<String, Object>> result = moneyBookService.getCategoryStatistic(req);
//        log.info("getCategoryStatistic response: {}", result);
//        return SysResponse.success(result);
//    }
//
//    @GetMapping("/getMonthlyData")
//    public SysResponse getMonthlyData(StatisticDataReq req) {
//        log.info("getMonthlyData: {}", req);
//
//        MonthlyDataResp result = moneyBookService.getMonthlyData(req);
//        log.info("getMonthlyData response: {}", result);
//        return SysResponse.success(result);
//    }
//
//
//    @PostMapping("/save")
//    public SysResponse save(@RequestBody MoneyBookReq req) {
//        log.info("save moneyBook: {}", req);
//        moneyBookService.saveMoneyBook(req,false);
//        return SysResponse.success();
//    }
//
//    @PostMapping("/edit")
//    public SysResponse edit(@RequestBody MoneyBook req) {
//        log.info("edit moneyBook: {}", req);
//        moneyBookService.editMoneyBook(req);
//        return SysResponse.success();
//    }
//}
