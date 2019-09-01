package com.qbk.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * shiro工具
 */
public class ShiroUtil {

    /**
     * 加密算法
     */
    public final static String HASH_ALGORITHM_NAME = "SHA-256";

    /**
     * 循环次数
     */
    public final static int HASH_ITERATIONS = 1024;

    /**
     * shiro密码加密工具类
     *
     * @param credentials 密码
     * @param saltSource  密码盐
     * @return
     */
    public static String sha256(String credentials, String saltSource) {
        return new SimpleHash(HASH_ALGORITHM_NAME, credentials, saltSource, HASH_ITERATIONS).toString();
    }

    /**
     * 随机生成盐值
     */
    public static String getRandomSalt() {
        String model = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuffer salt = new StringBuffer();
        char[] m = model.toCharArray();
        for (int i = 0; i < 6; i++) {
            char c = m[(int) (Math.random() * 36)];
            salt = salt.append(c);
        }
        return salt.toString();
    }

    public static void main(String[] args) {
        System.out.println(sha256("123456","zz_kk"));
        System.out.println("e37e4d885ee18239c1dcd04a85bfb299aac3231d695fefddfc7c1eb208fb75be".length());

        System.out.println(getRandomSalt() );
    }

}
