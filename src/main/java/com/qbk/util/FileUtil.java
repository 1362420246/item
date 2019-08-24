package com.qbk.util;

import cn.hutool.core.util.URLUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.system.ApplicationHome;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * 文件工具
 *  自动将所有域对象修改为static；而且自动创建一个私有的构造器：抛出异常，无法实例化
 */
@Slf4j
@UtilityClass
public class FileUtil {

    /**
     * 获取程序主路径
     */
    public String getHomePath(){
        return new ApplicationHome().toString();
    }

    /**
     *  下载
     * @param urlStr url路径
     */
    public boolean downLoadFile(String urlStr , HttpServletResponse response ) {
        if(urlStr == null){
           return false;
        }
        BufferedOutputStream output = null;
        try {
            //获取原文件名
            String path = new URL(urlStr).getPath();
            String fileName = path.substring(path.lastIndexOf("/") + 1);
            //解决中文/空格 转码
            urlStr = URLUtil.encode(urlStr);
            //获取二进制
            byte[] bytes = IOUtils.toByteArray(new URL(urlStr));
            //设置文件ContentType类型
            response.setContentType("multipart/form-data;charset=GBK");
            //文件名转码
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1");
            //设置文件头：最后一个参数是设置下载文件名
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName );
            //从response 获取 out流
            output = new BufferedOutputStream(response.getOutputStream());
            //写流
            output.write(bytes);
            //刷新
            output.flush();
            response.flushBuffer();
            return true ;
        } catch (IOException e) {
            log.error("文件下载错误",e);
            return false ;
        }finally {
            if(output != null){
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
