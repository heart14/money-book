package com.liiwe.moneybook.controller;

import com.liiwe.moneybook.base.bean.model.SysResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wfli
 * @since 2025/6/9 15:22
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class SystemController {


    @GetMapping("/aa")
    public SysResponse test(){
        return SysResponse.success("test jwt", null);
    }
}
