package com.qbk.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 自己封装的token
 * 类似于 UsernamePasswordToken
 * 用于 token 校验时
 */
public class JWTToken implements AuthenticationToken {

    private String token;

    public String getToken() {
        return token;
    }

    JWTToken(){}

    JWTToken(final String token){
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return getToken();
    }

    @Override
    public Object getCredentials() {
        return getToken();
    }
}
