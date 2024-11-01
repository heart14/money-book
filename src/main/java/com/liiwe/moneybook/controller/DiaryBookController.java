package com.liiwe.moneybook.controller;

import com.liiwe.moneybook.base.bean.model.SysResponse;
import com.liiwe.moneybook.base.bean.request.DiaryBookRequest;
import com.liiwe.moneybook.service.DiaryBookService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author wfli
 * @since 2024/11/1 10:32
 */
@RestController
@RequestMapping(("/diary"))
@Slf4j
public class DiaryBookController {

    private final DiaryBookService diaryBookService;

    public DiaryBookController(DiaryBookService diaryBookService) {
        this.diaryBookService = diaryBookService;
    }

    @PostMapping("/record")
    public SysResponse record(@Valid @RequestBody DiaryBookRequest request) {
        log.info("diary:{}", request);
        return SysResponse.success(diaryBookService.record(request));
    }

    @GetMapping("/query")
    public SysResponse query(DiaryBookRequest request) {
        log.info("diary:{}", request);
        return SysResponse.success(diaryBookService.query(request));
    }
}
