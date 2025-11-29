package com.liiwe.moneybook.base.exceptions;

import com.liiwe.moneybook.base.bean.model.SysResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wfli
 * @since 2024/9/26 14:55
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({org.springframework.web.HttpRequestMethodNotSupportedException.class})
    public SysResponse restfulHandler(org.springframework.web.HttpRequestMethodNotSupportedException e1) {
        log.error("请求方式错误");
        return SysResponse.fail(e1.getMessage());
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public SysResponse handleValidationExceptions(MethodArgumentNotValidException e2) {
        log.error("参数校验失败");
        Map<String, String> errors = new HashMap<>();
        e2.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return SysResponse.fail(errors.toString());
    }

    @ExceptionHandler({Exception.class})
    public SysResponse exceptionHandler(Exception e) {
        log.error("系统异常：{}", e.getMessage(), e);
        return SysResponse.fail(e.getMessage());
    }
}
