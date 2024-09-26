package com.liiwe.moneybook.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.liiwe.moneybook.base.bean.entity.DailyRecord;
import com.liiwe.moneybook.base.bean.request.DailyRecordQueryRequest;
import com.liiwe.moneybook.base.bean.request.DailyRecordSaveRequest;
import com.liiwe.moneybook.service.DailyRecordService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author wfli
 * @since 2024/9/25 18:44
 */
@RestController
@RequestMapping(("/daily"))
@Slf4j
public class DailyRecordController {

    private final DailyRecordService dailyRecordService;

    public DailyRecordController(DailyRecordService dailyRecordService) {
        this.dailyRecordService = dailyRecordService;
    }

    /**
     * 记一笔
     * @param request
     * @return
     */
    @PostMapping("/save")
    public DailyRecord save(@Valid @RequestBody DailyRecordSaveRequest request) {
        return dailyRecordService.save(request);
    }

    /**
     * 查一下
     * @param request
     * @return
     */
    @GetMapping("/query")
    public List<DailyRecord> query(@RequestBody DailyRecordQueryRequest request) {
        return dailyRecordService.query(request);
    }
}
