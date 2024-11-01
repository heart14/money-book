package com.liiwe.moneybook.base.bean.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wfli
 * @since 2024/11/1 10:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryBookRequest {

    private String date;

    @NotBlank(message = "日志内容不能为空")
    private String diary;

    private String username;
}
