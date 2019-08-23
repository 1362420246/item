package com.telecomyt.item.config.druid;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 选择器
 */
public class DruidImportSelector implements ImportSelector {

    /**
     * 导入配置类
     */
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{DruidDataSourceAutoConfigure.class.getName()};
    }
}
