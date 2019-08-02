package com.telecomyt.item.event;

import com.telecomyt.item.entity.log.SysLog;
import com.telecomyt.item.web.controller.LogController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: electrocar
 * @Package: com.telecomyt.electrocar.event
 * @ClassName: ServiceLogListener
 * @Description: 系统服务日志事件监听器
 * @Author: zhoupengbing
 * @CreateDate: 2019/3/18 14:49
 * @UpdateUser: zhoupengbing
 * @UpdateDate: 2019/3/18 14:49
 * @UpdateRemark:
 */
@Slf4j
@AllArgsConstructor
@Component
public class ServiceLogListener {

    private final LogController logController;

    @Async
    @EventListener(ServiceLogEvent.class)
    public void saveServiceLog(ServiceLogEvent event)throws Exception{
        SysLog log = (SysLog)event.getSource();
        //添加日志信息
        logController.add(log);
    }

}
