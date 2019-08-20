package com.telecomyt.item.handler;

import cn.hutool.json.JSONUtil;
import com.telecomyt.item.dto.resp.BaseResp;
import com.telecomyt.item.entity.log.ExceptionLog;
import com.telecomyt.item.entity.log.SysLog;
import com.telecomyt.item.enums.ResultStatus;
import com.telecomyt.item.event.ServiceLogEvent;
import com.telecomyt.item.exception.BasicException;
import com.telecomyt.item.util.LogUtils;
import com.telecomyt.item.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: gx-electrocar-ga
 * @Package: com.telecomyt.electrocar.handler
 * @ClassName: GlobalExceptionHandler
 * @Description:
 * @Author: zhoupengbing
 * @CreateDate: 2019/4/2 15:38
 * @UpdateUser: zhoupengbing
 * @UpdateDate: 2019/4/2 15:38
 * @UpdateRemark:
 */
@Slf4j
@ControllerAdvice(annotations = {Controller.class, RestController.class})
public class GlobalExceptionHandler {

    /**
     * 系统异常处理，比如：404,500
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResp<?> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {

        BaseResp baseResp = new BaseResp();
        //获取日志信息
        SysLog sysLog = LogUtils.getServiceLog();
        sysLog.setTitle("异常日志");

        if (e instanceof BasicException) {
            baseResp.setCode(((BasicException) e).getCode());
            baseResp.setMessage(((BasicException) e).getMsg());

            log.error("\n方法调用异常异常，\n请求路径是：{},\n请求方法是：{}," +
                            "\n请求参数是：{},\n异常信息是：{}",req.getRequestURI().toString(),req.getMethod(),
                    JSONUtil.parseFromMap(req.getParameterMap()),((BasicException)e).getMsg());

        } else {
            baseResp.setMessage(ResultStatus.http_status_internal_server_error.getErrorMsg());
            baseResp.setCode(ResultStatus.http_status_internal_server_error.getErrorCode());

            log.error("\n方法调用异常异常，\n请求路径是：{},\n请求方法是：{}," +
                            "\n请求参数是：{},\n异常信息是：{}",req.getRequestURI().toString(),req.getMethod(),
                    JSONUtil.parseFromMap(req.getParameterMap()),e.getMessage());
        }
        e.printStackTrace();
        //获取异常信息
        baseResp.setData("");
        sysLog.setReturnMsg(JSONUtil.toJsonStr(baseResp));
        sysLog.setException(JSONUtil.toJsonStr(ExceptionLog.builder().cause(e.toString()).stackTrace(e.getStackTrace()).build()));
        SpringContextHolder.publishEvent(new ServiceLogEvent(sysLog));
        return baseResp;
    }


}
