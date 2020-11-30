package com.gjf.eduservice.config;


import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.gjf.eduservice.mapper")
public class EduConfig {

    /*逻辑删除插件*/
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    @Bean
    /*分页插件*/
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
