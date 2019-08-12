package com.telecomyt.item.bus;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author zhoupengbing
 * @packageName com.telecomyt.forum.component
 * @email zhoupengbing@telecomyt.com.cn
 * @description  推送选择器
 * @createTime 2019年07月17日 12:36:00
 * @Version v1.0
 */
public class DxytBusPushImportSelector implements ImportSelector{
    
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{DxytBusPushConfiguration.class.getName()};
    }

}
