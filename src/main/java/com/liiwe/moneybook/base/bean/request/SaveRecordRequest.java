package com.liiwe.moneybook.base.bean.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wfli
 * @since 2024/9/26 14:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveRecordRequest {

    private String date;

    private String title;

    @NotBlank(message = "金额不能为空")
    private String amount;

    private String type;

    private String category;

    private String remark;

    @NotBlank(message = "用户不能为空")
    private String username;
}
