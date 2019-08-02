package com.telecomyt.item.event;

import org.springframework.context.ApplicationEvent;

/**
 * @ProjectName: electrocar
 * @Package: com.telecomyt.electrocar.event
 * @ClassName: ServiceLogEvent
 * @Description: 系统业务日志事件
 * @Author: zhoupengbing
 * @CreateDate: 2019/3/18 14:48
 * @UpdateUser: zhoupengbing
 * @UpdateDate: 2019/3/18 14:48
 * @UpdateRemark:
 */
public class ServiceLogEvent extends ApplicationEvent {

    public ServiceLogEvent(Object source) {
        super(source);
    }

}
