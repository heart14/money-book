package com.liiwe.moneybook.base.bean.domain.mb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lwf14
 * @since 2025/12/10 15:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageUserReq {

    private int current;

    private int size;

    private String username;

    private String nickname;

}
