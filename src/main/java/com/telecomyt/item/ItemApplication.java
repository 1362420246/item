package com.telecomyt.item;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.telecomyt.item.bus.EnableDxytBusPush;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@EnableDxytBusPush
/**
 * 去掉Druid数据源自动加载，因为在test环境中使用HikariDataSource数据会受到影响
 * 在配置类中选择dev和pro环境时候重新加载Druid数据源
 */
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class ItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemApplication.class, args);
    }

}
