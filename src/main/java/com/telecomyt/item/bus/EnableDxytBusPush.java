package com.telecomyt.item.bus;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zhoupengbing
 * @packageName com.telecomyt.forum.component
 * @email zhoupengbing@telecomyt.com.cn
 * @description 启用总线推送
 * @createTime 2019年07月17日 12:34:00
 * @Version v1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DxytBusPushImportSelector.class)
public @interface EnableDxytBusPush {
}
