package com.qbk.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 类似于 UsernamePasswordToken
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
