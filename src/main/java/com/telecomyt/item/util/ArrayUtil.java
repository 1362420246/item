package com.telecomyt.item.util;

/**
 * @author zhoupengbing
 * @packageName com.telecomyt.forum.utils
 * @email zhoupengbing@telecomyt.com.cn
 * @description
 * @createTime 2019年07月17日 15:54:00
 * @Version v1.0
 */
public class ArrayUtil {

    public static String correctNumber(String number){
        String a = "0000";
        if(number.length() < 4){
            a = a.substring(0,4-number.length()) + number;
        }
        return a;
    }

}
