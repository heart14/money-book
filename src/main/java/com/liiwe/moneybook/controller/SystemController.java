package com.liiwe.moneybook.controller;

import com.alibaba.excel.EasyExcel;
import com.liiwe.moneybook.base.bean.entity.DiaryBook;
import com.liiwe.moneybook.base.bean.entity.MoneyBook;
import com.liiwe.moneybook.base.bean.model.MoneyBookImportTemplate;
import com.liiwe.moneybook.base.bean.model.SysResponse;
import com.liiwe.moneybook.base.listener.MoneyBookImportListener;
import com.liiwe.moneybook.service.DiaryBookService;
import com.liiwe.moneybook.service.MoneyBookService;
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

    public SystemController(MoneyBookService moneyBookService, DiaryBookService diaryBookService) {
        this.moneyBookService = moneyBookService;
        this.diaryBookService = diaryBookService;
    }

    @PostMapping("/import")
    public SysResponse importFromExcel(@RequestBody MultipartFile file, String sheet) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        log.info("test import, {}", file.getOriginalFilename());

        MoneyBookImportListener listener = new MoneyBookImportListener();
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
}
