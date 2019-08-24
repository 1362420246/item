package com.qbk.config;

import com.qbk.config.druid.DruidConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

/**
 *  mybatis配置文件
 *  扫描mapper接口
 */
@Configuration
@MapperScan(basePackages = {"com.qbk.web.mapper" ,"com.qbk.log.mapper"})
@AutoConfigureAfter(DruidConfig.class)
public class MybatisConfig {
}
