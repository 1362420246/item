package com.telecomyt.item.config;

import com.telecomyt.item.config.druid.DruidConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

/**
 *  mybatis配置文件
 * @author quboka
 */
@Configuration
@MapperScan(basePackages = "com.telecomyt.item.web.mapper")
@AutoConfigureAfter(DruidConfig.class)
public class MybatisConfig {
}
