package com.telecomyt.item.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 *  mybatis配置文件
 * @author quboka
 */
@Configuration
@MapperScan(basePackages = "com.telecomyt.item.web.mapper")
public class MybatisConfig {
}
