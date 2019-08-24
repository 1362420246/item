package com.qbk.log.event;

import org.springframework.context.ApplicationEvent;

/**
 * 业务日志事件
 */
public class ServiceLogEvent extends ApplicationEvent {

    public ServiceLogEvent(Object source) {
        super(source);
    }

}
