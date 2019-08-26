package com.qbk.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created with IntelliJ IDEA
 *
 * @Author yuanhaoyue swithaoy@gmail.com
 * @Description token
 * @Date 2018-04-09
 * @Time 16:54
 */
public class JWTToken implements AuthenticationToken {
    /**
     * The username
     */
    private String username;

    /**
     * The password, in char[] format
     */
    private String password;

   JWTToken(){}
   JWTToken(String username){
       this.username = username;
   }
   public JWTToken(final String username, final String password){
       this.username = username;
       this.password = password;
   }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public Object getCredentials() {
        return password;
    }
}
