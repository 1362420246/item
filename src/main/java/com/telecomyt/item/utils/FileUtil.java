package com.telecomyt.item.utils;

import com.telecomyt.item.constant.CommonConstant;
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

    /**
     *  获取上传路径 ： /path/cardid/nowDate/
     * @param path 类型的路径中段
     * @param cardid 身份证号
     * @return
     */
    public static String getUpload(String path , String cardid ){
        return FileUtil.getHomePath() + path+ cardid + "/" + DateUtil.getNow() + "/" ;
    }

    /**
     * 根据路径截取 相对路径
     * @param path 全路径
     */
    public static String getRelativePath(String path ){
        return path.substring( FileUtil.getHomePath().length());
    }

    public static void main(String[] args) {
        String homePath = getHomePath();
        System.out.println(homePath);
    }
}
