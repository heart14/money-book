package com.liiwe.moneybook.controller;

import com.liiwe.moneybook.base.bean.domain.mb.TotalAmountReq;
import com.liiwe.moneybook.base.bean.domain.mb.TotalAmountResp;
import com.liiwe.moneybook.base.bean.model.SysResponse;
import com.liiwe.moneybook.service.MoneyBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wfli
 * @since 2024/9/25 18:44
 */
@RestController
@RequestMapping(("/moneybook"))
@Slf4j
public class MoneyBookController {

    private final MoneyBookService moneyBookService;

    public MoneyBookController(MoneyBookService moneyBookService) {
        this.moneyBookService = moneyBookService;
    }

    @GetMapping("/getTotalAmount")
    public SysResponse getTotalAmount(TotalAmountReq req){
        log.info("getTotalAmount: {}", req);
        TotalAmountResp totalAmount = moneyBookService.getTotalAmount(req);
        return SysResponse.success(totalAmount);
    }

}
