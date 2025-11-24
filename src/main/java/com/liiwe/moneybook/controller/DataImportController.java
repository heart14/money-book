package com.liiwe.moneybook.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.liiwe.moneybook.base.bean.domain.mb.MoneyBookReq;
import com.liiwe.moneybook.base.bean.model.MoneyBookTemplate;
import com.liiwe.moneybook.base.bean.model.SysResponse;
import com.liiwe.moneybook.base.listener.MoneyBookImportListener;
import com.liiwe.moneybook.service.biz.DataImportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author wfli
 * @since 2025/6/9 15:22
 */
@RestController
@RequestMapping("/import")
@Slf4j
public class DataImportController {

    private final DataImportService dataImportService;

    public DataImportController(DataImportService dataImportService) {
        this.dataImportService = dataImportService;
    }

    @PostMapping("/excel")
    public SysResponse importFromExcel(@RequestBody MultipartFile file, String sheet) {
        log.info("test import, {}", file.getOriginalFilename());
        MoneyBookImportListener listener = new MoneyBookImportListener();
        try {
            EasyExcel.read(file.getInputStream(), MoneyBookTemplate.class, listener).sheet(sheet).doRead();
        } catch (IOException e) {
            throw new RuntimeException("模板导入失败");
        }
        dataImportService.excel(listener.getRecords());
        return SysResponse.success();
    }

    @PostMapping("/screen")
    public SysResponse screen(String title, String amount, String time, String type, String category, String remark, String username) {

        DateTime parse;
        if (time.contains("年")) {
            parse = DateUtil.parse(time, "yyyy年MM月dd日 hh:mm:ss");
        } else {
            parse = DateUtil.parse(time, "yyyy-MM-dd hh:mm:ss");
        }
        log.info("time:{}", parse);


        MoneyBookReq moneyBookReq = new MoneyBookReq();
        moneyBookReq.setTitle(title);
        moneyBookReq.setAmount(amount);
        moneyBookReq.setDatetime(DateUtil.format(parse, "yyyy-MM-dd hh:mm:ss"));
        moneyBookReq.setType(type);
//        moneyBookReq.setCategoryId(categoryId);
        moneyBookReq.setRemark(remark);
        moneyBookReq.setUsername(username);

        log.info("save from mobile:{}", moneyBookReq);
        return SysResponse.success();
    }
}
