package com.liiwe.moneybook.base.bean.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wfli
 * @since 2024/9/26 14:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysResponse {

    private Integer code;

    private String msg;

    private Object data;

    public static SysResponse success() {
        return success(null);
    }

    public static SysResponse success(Object data) {
        return success("SUCCESS", data);
    }

    public static SysResponse success(String msg, Object data) {
        return new SysResponse(200, msg, data);
    }


    public static SysResponse fail(String msg) {
        return new SysResponse(201, msg, null);
    }
}
