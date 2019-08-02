package com.telecomyt.item.annotation;

import java.lang.annotation.*;

/**
 * @ProjectName: electrocar
 * @Package: com.telecomyt.electrocar.annotation
 * @ClassName: ServiceLog
 * @Description: 业务操作日志
 * @Author: zhoupengbing
 * @CreateDate: 2019/3/18 14:37
 * @UpdateUser: zhoupengbing
 * @UpdateDate: 2019/3/18 14:37
 * @UpdateRemark:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceLog {

    /**
     * 日志描述
     * @return
     */
    String value() default "";

}
