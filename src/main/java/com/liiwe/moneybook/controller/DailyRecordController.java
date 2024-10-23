package com.liiwe.moneybook.controller;

import com.alibaba.excel.EasyExcel;
import com.liiwe.moneybook.base.bean.entity.MoneyBookRecord;
import com.liiwe.moneybook.base.bean.model.RecordExcel;
import com.liiwe.moneybook.base.bean.model.SysResponse;
import com.liiwe.moneybook.base.bean.request.QueryRecordRequest;
import com.liiwe.moneybook.base.bean.request.SaveRecordRequest;
import com.liiwe.moneybook.base.listener.RecordExcelListener;
import com.liiwe.moneybook.service.MoneyBookService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author wfli
 * @since 2024/9/25 18:44
 */
@RestController
@RequestMapping(("/daily"))
@Slf4j
public class DailyRecordController {

    private final MoneyBookService moneyBookService;

    public DailyRecordController(MoneyBookService moneyBookService) {
        this.moneyBookService = moneyBookService;
    }

    /**
     * 保存记账记录
     * @param request
     * @return
     */
    @GetMapping("/record")
    public SysResponse record(@Valid SaveRecordRequest request) {
        MoneyBookRecord record = moneyBookService.save(request);
        return SysResponse.success(record);
    }

    /**
     * 查询记账记录
     * @param request
     * @return
     */
    @GetMapping("/query")
    public SysResponse query(QueryRecordRequest request) {
        log.info("query:{}", request);
        return SysResponse.success(moneyBookService.query(request));
    }

    /**
     * 查询月支出统计图数据
     * @param year
     * @return
     */
    @GetMapping("/monthData/{year}")
    public SysResponse monthData(@PathVariable("year") String year) {
        log.info("query:{}", year);
        return SysResponse.success(moneyBookService.queryMonthDataByYear(year));
    }

    /**
     * 查询分类支出统计图数据
     * @param year
     * @return
     */
    @GetMapping("/categoryData/{year}")
    public SysResponse categoryData(@PathVariable("year") String year) {
        log.info("query:{}", year);
        return SysResponse.success(moneyBookService.queryCategoryDataByYear(year));
    }

    /**
     * 查询月分类统计表数据
     * @param year
     * @return
     */
    @GetMapping("/monthCategoryData/{year}")
    public SysResponse monthCategoryData(@PathVariable("year") String year) {
        log.info("query:{}", year);
        return SysResponse.success(moneyBookService.queryMonthCategoryDataByYear(year));
    }

    /**
     * 从Excel导入记账记录
     * @param map
     * @return
     */
    @PostMapping("/upload")
    public List<RecordExcel> upload(@RequestBody Map<String, String> map) {
        RecordExcelListener listener = new RecordExcelListener();
        EasyExcel.read(map.get("path"), RecordExcel.class, listener)
                .sheet(map.get("sheet"))
                .doRead();
        List<RecordExcel> records = listener.getData();
        moneyBookService.upload(records);
        return records;
    }
}
