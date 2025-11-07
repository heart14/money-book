package com.liiwe.moneybook.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.liiwe.moneybook.base.bean.domain.mb.MoneyBookReq;
import com.liiwe.moneybook.base.bean.entity.DiaryBook;
import com.liiwe.moneybook.base.bean.entity.MoneyBook;
import com.liiwe.moneybook.base.bean.model.MoneyBookImportTemplate;
import com.liiwe.moneybook.base.bean.model.SysResponse;
import com.liiwe.moneybook.base.listener.MoneyBookImportListener;
import com.liiwe.moneybook.base.utils.CategoryMappingUtil;
import com.liiwe.moneybook.service.DiaryBookService;
import com.liiwe.moneybook.service.MoneyBookService;
import com.liiwe.moneybook.service.SysCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author wfli
 * @since 2025/6/9 15:22
 */
@RestController
@RequestMapping("/sys")
@Slf4j
public class SystemController {

    private final MoneyBookService moneyBookService;
    private final DiaryBookService diaryBookService;
    private final CategoryMappingUtil categoryMappingUtil;

    public SystemController(MoneyBookService moneyBookService, DiaryBookService diaryBookService,  CategoryMappingUtil categoryMappingUtil) {
        this.moneyBookService = moneyBookService;
        this.diaryBookService = diaryBookService;
        this.categoryMappingUtil = categoryMappingUtil;
    }

    @PostMapping("/import")
    public SysResponse importFromExcel(@RequestBody MultipartFile file, String sheet) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        log.info("test import, {}", file.getOriginalFilename());

        MoneyBookImportListener listener = new MoneyBookImportListener(categoryMappingUtil);
        try {
            EasyExcel.read(file.getInputStream(), MoneyBookImportTemplate.class, listener).sheet(sheet).doRead();
        } catch (IOException e) {
            throw new RuntimeException("模板导入失败");
        }

        List<MoneyBook> moneyBookList = listener.getMoneyBookList(name);
        List<DiaryBook> diaryBookList = listener.getDiaryBookList(name);

        moneyBookService.importMoneyBook(moneyBookList);
        diaryBookService.importDiaryBook(diaryBookList);

        return SysResponse.success(moneyBookList);
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

        int categoryId = categoryMappingUtil.getCategoryId(category);


        MoneyBookReq moneyBookReq = new MoneyBookReq();
        moneyBookReq.setTitle(title);
        moneyBookReq.setAmount(amount);
        moneyBookReq.setDatetime(DateUtil.format(parse, "yyyy-MM-dd hh:mm:ss"));
        moneyBookReq.setType(type);
        moneyBookReq.setCategoryId(categoryId);
        moneyBookReq.setRemark(remark);
        moneyBookReq.setUsername(username);

        log.info("save from mobile:{}", moneyBookReq);

        moneyBookService.saveMoneyBook(moneyBookReq, true);

        return SysResponse.success();
    }
}
