package com.liiwe.moneybook.base.config;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus 分页查询
 * 添加此配置以解决分页查询结果中的total与pages字段为0的问题
 * @author wfli
 * @since 2025/6/19 15:58
 */
@Configuration
public class MybatisPlusPageHelperConfig {

    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor(){
        return new PaginationInnerInterceptor();
    }
}
