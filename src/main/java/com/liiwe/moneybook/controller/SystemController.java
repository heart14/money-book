package com.liiwe.moneybook.controller;

import com.liiwe.moneybook.base.bean.domain.system.LoginReq;
import com.liiwe.moneybook.base.bean.entity.SysUser;
import com.liiwe.moneybook.base.bean.model.SysResponse;
import com.liiwe.moneybook.base.security.JwtUtils;
import com.liiwe.moneybook.service.SysUserService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
