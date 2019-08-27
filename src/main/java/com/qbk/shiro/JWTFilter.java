package com.qbk.shiro;

import com.qbk.exception.BasicException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Objects;

/**
 *  过滤器
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {

    /**
     * 登录标识
     */
    private static String LOGIN_SIGN = "token";

    /**
     * 是否放行
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            return executeLogin(request, response);
        }else {
            return false;
        }
    }

    /**
     * 检测用户是否登录
     * 检测header里面是否包含token的key
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authToken = req.getHeader(LOGIN_SIGN);
        return StringUtils.isNotBlank(authToken);

    }

    /**
     * 解析jwt 进行认证校验
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authToken = req.getHeader(LOGIN_SIGN);
        String username = JWTUtil.getUsername(authToken);
       if( StringUtils.isBlank(username)){
            return false ;
        }
        JWTToken token = new JWTToken(username);
        getSubject(request, response).login(token);
        return true;
    }


}
