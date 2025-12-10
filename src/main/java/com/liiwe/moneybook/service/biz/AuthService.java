package com.liiwe.moneybook.service.biz;

import com.liiwe.moneybook.base.bean.domain.auth.RegisterReq;

/**
 * @author lwf14
 * @since 2025/11/24 11:32
 */
public interface AuthService {

    void register(RegisterReq req);
}
