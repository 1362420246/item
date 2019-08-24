package com.qbk.config.druid;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 导入选择器
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DruidImportSelector.class)
public @interface EnableDruid {
}
