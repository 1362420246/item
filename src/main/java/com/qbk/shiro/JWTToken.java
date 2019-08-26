package com.qbk.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 代替 UsernamePasswordToken
 */
public class JWTToken implements AuthenticationToken {
    /**
     * The username
     */
    private String username;

    /**
     * The password
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    JWTToken(){}

   JWTToken(final String username){
       this.username = username;
   }
   public JWTToken(final String username, final String password){
       this.username = username;
       this.password = password;
   }

    @Override
    public Object getPrincipal() {
        return getUsername();
    }

    @Override
    public Object getCredentials() {
        return getUsername();
    }
}
