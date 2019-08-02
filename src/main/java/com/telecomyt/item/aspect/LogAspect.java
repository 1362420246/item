package com.telecomyt.item.aspect;

import cn.hutool.json.JSONUtil;
import com.telecomyt.item.annotation.ServiceLog;
import com.telecomyt.item.entity.log.SysLog;
import com.telecomyt.item.event.ServiceLogEvent;
import com.telecomyt.item.utils.LogUtils;
import com.telecomyt.item.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: electrocar
 * @Package: com.telecomyt.electrocar.aspect
 * @ClassName: LogAspect
 * @Description: 日志切面类
 * @Author: zhoupengbing
 * @CreateDate: 2019/3/18 14:39
 * @UpdateUser: zhoupengbing
 * @UpdateDate: 2019/3/18 14:39
 * @UpdateRemark:
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    /**
     * 系统操作日志切面
     * @param point
     * @param serviceLog
     * @return
     * @throws Throwable
     */
    @Around("@annotation(serviceLog)")
    public Object arround(ProceedingJoinPoint point, ServiceLog serviceLog)throws Throwable{

        //类名
        String className = point.getTarget().getClass().getName();
        //方法名
        String methodName = point.getSignature().getName();
        log.debug("请求的类名是:{},方法名是:{}",className,methodName);
        SysLog sLog = LogUtils.getServiceLog();
        long startTime = System.currentTimeMillis();
        Object proceed = point.proceed();
        long endTime = System.currentTimeMillis();
        sLog.setTitle(serviceLog.value());
        sLog.setTime(endTime - startTime);
        sLog.setReturnMsg(JSONUtil.toJsonStr(proceed));

        //发布日志事件
        SpringContextHolder.publishEvent(new ServiceLogEvent(sLog));
        return proceed;
    }

}
