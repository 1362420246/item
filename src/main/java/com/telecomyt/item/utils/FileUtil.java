package com.telecomyt.item.utils;

import org.springframework.boot.system.ApplicationHome;

/**
 * 文件工具
 */
public class FileUtil {

    /**
     * 获取程序主路径
     */
    public static String getHomePath(){
        return new ApplicationHome().toString();
    }

    public static void main(String[] args) {
        String homePath = getHomePath();
        System.out.println(homePath);
    }
}
