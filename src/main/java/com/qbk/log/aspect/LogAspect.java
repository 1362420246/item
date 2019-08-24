package com.qbk.log.aspect;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import com.qbk.log.entity.SysLog;
import com.qbk.log.annotation.ServiceLog;
import com.qbk.log.event.ServiceLogEvent;
import com.qbk.log.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

/**
 * 业务操作日志
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    /**
     * 系统操作日志切面
     */
    @Around("@annotation(serviceLog)")
    public Object arround(ProceedingJoinPoint point, ServiceLog serviceLog)throws Throwable{
        //请求容器
        HttpServletRequest request = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        //类名
        String className = point.getTarget().getClass().getName();
        //方法名
        String methodName = point.getSignature().getName();
        log.debug("请求的类名是:{},方法名是:{}",className,methodName);
        long startTime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        //执行方法并返回结果
        Object proceed = point.proceed();
        long endTime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        //TODO 获取当前用户信息
        String username = "" ;
        //参数
        String params = JSONUtil.toJsonStr(point.getArgs()).replaceAll("[\\[\\]]", "");
        SysLog sLog = SysLog.builder()
                .createUser(username)//用户
                .time(endTime - startTime)//耗时
                .title(serviceLog.value())//自定义注解的值
                .userAgent(request.getHeader("user-agent"))
                .requestUri(URLUtil.getPath(request.getRequestURI()))//uri
                .remoteAddr(ServletUtil.getClientIP(request))//ip地址
                .returnMsg(JSONUtil.toJsonStr(proceed))//返回结果
                .params(Base64.encode(params))//参数
                .build();
        //发布日志事件
        SpringContextHolder.publishEvent(new ServiceLogEvent(sLog));
        return proceed;
    }
}
