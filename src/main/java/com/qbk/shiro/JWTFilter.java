package com.qbk.shiro;

import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.qbk.exception.BasicException;
import com.qbk.result.BaseResultGenerator;
import com.qbk.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
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
     * 身份校验
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        boolean loggedIn = false;
        HttpServletRequest req = (HttpServletRequest) request;
        //从请求头里获取token
        String authToken = req.getHeader(LOGIN_SIGN);
        //检测header里面是否包含token的key
        if(StringUtils.isNotBlank(authToken)){
            loggedIn = executeLogin(request, response);
        }else {
            String path = URLUtil.getPath(req.getRequestURI());
            log.debug("请求：{}，不包含token",path );
            HttpUtil.responseSendErrorJson((HttpServletResponse) response,"缺少token");
        }
        return loggedIn;
    }

    /**
     * 进行登陆
     * 解析jwt 进行认证校验
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authToken = req.getHeader(LOGIN_SIGN);

        //从token中获取name
        String username = JWTUtil.getUsername(authToken);
        if( StringUtils.isBlank(username)){
            log.debug("token:{},中无用户信息",authToken );
            HttpUtil.responseSendErrorJson((HttpServletResponse) response,"错误的token");
            return false ;
        }

        //校验token
        try {
            JWTUtil.verify(authToken);
        } catch (UnsupportedEncodingException e) {
            log.error("当前Java平台实现不支持UTF-8字符编码。", e );
            HttpUtil.responseSendErrorJson((HttpServletResponse) response,"错误的token");
            return false ;
        }catch (JWTDecodeException e){
            log.error("错误的token组成。", e );
            HttpUtil.responseSendErrorJson((HttpServletResponse) response,"错误的token");
            return false ;
        }catch (SignatureVerificationException e){
            log.debug("无效的token:{}",authToken );
            HttpUtil.responseSendErrorJson((HttpServletResponse) response,"无效的token");
            return false ;
        }catch (TokenExpiredException e){
            log.debug("token:{},已过期",authToken );
            HttpUtil.responseSendErrorJson((HttpServletResponse) response,"token已过期");
            return false ;
        }

        //TODO 这个token无法续时
        //将用户名封装自定义token中，提交给Shiro处理，触发认证方法
        JWTToken token = new JWTToken(username);
        getSubject(request, response).login(token);
        return true;
    }


}
