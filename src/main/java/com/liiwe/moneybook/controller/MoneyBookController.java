package com.liiwe.moneybook.controller;

import com.liiwe.moneybook.base.bean.domain.mb.BillListReq;
import com.liiwe.moneybook.base.bean.domain.mb.TotalAmountReq;
import com.liiwe.moneybook.base.bean.domain.mb.TotalAmountResp;
import com.liiwe.moneybook.base.bean.entity.MoneyBook;
import com.liiwe.moneybook.base.bean.model.SysResponse;
import com.liiwe.moneybook.service.MoneyBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    /**
     * 按日期查询总支出与收入数据
     * @param req yyyy | yyyy-MM | yyyy-MM-dd
     */
    @GetMapping("/getTotalAmount")
    public SysResponse getTotalAmount(TotalAmountReq req){
        log.info("getTotalAmount: {}", req);
        TotalAmountResp totalAmount = moneyBookService.getTotalAmount(req);
        log.info("getTotalAmount response: {}", totalAmount);
        return SysResponse.success(totalAmount);
    }


    /**
     * 按日期查询账单记录集合
     */
    @GetMapping("/getBillList")
    public SysResponse getBillList(BillListReq req){
        log.info("getBillList: {}", req);
        List<MoneyBook> billList = moneyBookService.getBillList(req);
        log.info("getBillList response: {}", billList);
        return SysResponse.success(billList);
    }
}
