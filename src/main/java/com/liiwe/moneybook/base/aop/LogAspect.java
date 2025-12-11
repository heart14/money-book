package com.liiwe.moneybook.base.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.stream.Stream;

/**
 * @author lwf14
 * @since 2025/12/11 10:14
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    // 注入 Spring 容器里的 ObjectMapper
    private final ObjectMapper objectMapper;

    // 1. 切点：所有 Controller 的 public 方法
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *) && execution(public * *(..))")
    public void controller() {
    }

    // 2. 环绕通知
    @Around("controller()")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String url = request.getRequestURI();
        String httpMethod = request.getMethod();
        String classMethod = pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();

        // 2.1 请求参数
        Object[] args = pjp.getArgs();
        String params = args == null || args.length == 0 ? ""
                : objectMapper.writeValueAsString(Stream.of(args).filter(a -> !(a instanceof HttpServletRequest)).toArray());

        log.info("【REQ】{} {} {} 参数: {}", httpMethod, url, classMethod, params);

        // 2.2 执行目标方法
        long start = System.currentTimeMillis();
        Object result;
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            log.warn("【RESP-ERROR】{} {} 耗时:{}ms 异常:{}", httpMethod, url,
                    System.currentTimeMillis() - start, e.getMessage());
            throw e;
        }

        // 2.3 返回结果
        String resp = objectMapper.writeValueAsString(result);
        log.info("【RESP】{} {} 耗时:{}ms 返回值:{}", httpMethod, url,
                System.currentTimeMillis() - start, resp);
        return result;
    }
}