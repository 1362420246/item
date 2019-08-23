package com.telecomyt.item.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import com.telecomyt.item.entity.log.SysLog;
import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @ProjectName: electrocar
 * @Package: com.telecomyt.electrocar.utils
 * @ClassName: LogUtils
 * @Description: 日志工具类
 * @Author: zhoupengbing
 * @CreateDate: 2019/3/18 15:05
 * @UpdateUser: zhoupengbing
 * @UpdateDate: 2019/3/18 15:05
 * @UpdateRemark:
 */
@UtilityClass
public class LogUtils {

    /**
     * 获取业务服务日志
     * @return
     * @throws Exception
     */
    public SysLog getServiceLog()throws Exception{
        HttpServletRequest request = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        //获取当前用户信息
        String username = "" ;
        //请求参数
        String params = JSONUtil.toJsonStr(request.getParameterMap()).replaceAll("[\\[\\]]", "");
        return SysLog.builder()
                .createUser(username)
                .type("1")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .time(0L)
                .remoteAddr(ServletUtil.getClientIP(request))
                .requestUri(URLUtil.getPath(request.getRequestURI()))
                .userAgent(request.getHeader("user-agent"))
                .params(Base64.encode(params))
                .build();
    }

    public static void main(String[] args) {
        System.out.println(new String(Base64.decode("eyJwYXNzd29yZCI6IjEyMzQ1NiIsInVzZXJuYW1lIjoiYWRtaW4ifQ==")));
    }

}
