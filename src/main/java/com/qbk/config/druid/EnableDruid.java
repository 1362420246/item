package com.qbk.config.druid;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 导入选择器
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//导入的一个或多个@Configuration}类
@Import(DruidImportSelector.class)
public @interface EnableDruid {
}
