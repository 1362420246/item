package com.qbk.log.event;

import com.qbk.log.entity.SysLog;
import com.qbk.log.mapper.LogMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 日志事件监听器
 */
@Slf4j
@AllArgsConstructor
@Component
public class ServiceLogListener {

    private final LogMapper logMapper;

    @Async
    @EventListener(ServiceLogEvent.class)
    public void saveServiceLog(ServiceLogEvent event)throws Exception{
        SysLog log = (SysLog)event.getSource();
        //添加日志信息
        logMapper.add(log);
    }

}
