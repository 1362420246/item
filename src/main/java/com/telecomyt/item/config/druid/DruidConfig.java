package com.telecomyt.item.config.druid;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 *  在配置类中选择dev和pro环境时候重新加载Druid数据源
 *  因为SpringBootApplication 中去掉了Druid自动配置
 *  @SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
 */
@Configuration
//指定环境
@Profile({ "dev","pro"})
//自定义注解
@EnableDruid
public class DruidConfig {

}
