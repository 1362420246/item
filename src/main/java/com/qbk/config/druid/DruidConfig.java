package com.qbk.config.druid;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 *  用不同的配置环境 + 自定义注解 + @Import注解  自动注入bean
 *
 *  在配置类中选择dev和pro环境时候重新加载Druid数据源
 *  因为SpringBootApplication 中去掉了Druid自动配置
 *  SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
 */
@Configuration
//指定环境
@Profile({ "dev" })
//自定义注解
@EnableDruid
public class DruidConfig {

}
