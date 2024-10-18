package com.liiwe.moneybook.controller;

import com.alibaba.excel.EasyExcel;
import com.liiwe.moneybook.base.bean.entity.MoneyBookRecord;
import com.liiwe.moneybook.base.bean.model.RecordExcel;
import com.liiwe.moneybook.base.bean.request.SaveRecordRequest;
import com.liiwe.moneybook.base.bean.request.QueryRecordRequest;
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
    @PostMapping("/save")
    public MoneyBookRecord save(@Valid @RequestBody SaveRecordRequest request) {
        return moneyBookService.save(request);
    }

    /**
     * 保存记账记录
     * @param request
     * @return
     */
    @GetMapping("/record")
    public MoneyBookRecord record(@Valid SaveRecordRequest request) {
        return moneyBookService.save(request);
    }

    /**
     * 查询记账记录
     * @param request
     * @return
     */
    @PostMapping("/query")
    public List<MoneyBookRecord> query(@RequestBody QueryRecordRequest request) {
        log.info("query:{}",request);
        return moneyBookService.query(request);
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
