package com.qbk.shiro;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 */
public class JWTUtil {
    /**
     * 过期时间 毫秒
     */
    private static final long EXPIRE_TIME = 5 * 60 * 1000;

    /**
     * 密钥
     */
    private static final String SECRET = "QBK_SHIRO_JWT";

    /**
     * 生成 token
     *
     * @param username 用户名
     * @return 加密的token
     */
    public static String createToken(String username) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            // 附带username信息
            return JWT.create()
                    .withIssuer(username)
                    //到期时间
                    .withExpiresAt(date)
                    //创建一个新的JWT，并使用给定的算法进行标记
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 校验 token 是否正确 ，是否过期
     * @param token    密钥
     * @throws UnsupportedEncodingException 当前Java平台实现不支持UTF-8字符编码
     * @throws SignatureVerificationException token中密钥不对
     * @throws TokenExpiredException token过期
     */
    public static void verify(String token) throws UnsupportedEncodingException,SignatureVerificationException ,TokenExpiredException{
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        verifier.verify(token);
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getIssuer();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 校验 token 是否正确
     *
     * @param token    密钥
     * @param username 用户名
     * @return 是否正确
     */
    public static boolean verify(String token, String username) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            //在token中附带了username信息
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(username)
                    .build();
            //验证 token
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String token1 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJxdWJva2EiLCJleHAiOjE1NjgzNjk0MDN9.1TbZGDq_chUDU6QFnT3HCS9xK4NUv_0u3tTFh_kf6Z4";
        String token2 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJxdWJva2EiLCJleHAiOjE1NjgzNzA4NDJ9.8kiTUxKMY2_CegFsmQ_dNqNkicgmOHkWcH_MR4h6fEk";

        DecodedJWT jwt = JWT.decode(token1);
        String issuer = jwt.getIssuer();
        System.out.println(issuer);

        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            verifier.verify(token1);
            System.out.println(0);
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            System.out.println(1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println(2);
        }catch (TokenExpiredException e){
            e.printStackTrace();
            System.out.println(3);
        }

        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256("QBK_SHIRO_JWT1");
        final String sign = JWT.create()
                .withIssuer( "quboka")
                //到期时间
                .withExpiresAt(date)
                //创建一个新的JWT，并使用给定的算法进行标记
                .sign(algorithm);
        System.out.println(sign);

    }
}
