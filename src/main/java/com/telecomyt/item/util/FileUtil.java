package com.telecomyt.item.util;

import cn.hutool.core.util.URLUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.system.ApplicationHome;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * 文件工具
 */
@Slf4j
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

    /**
     *  下载
     * @param urlStr url路径
     */
    public static boolean downLoadFile(String urlStr , HttpServletResponse response ) {
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

    public static void main(String[] args) throws IOException {
        String str = "http://127.0.0.1:8083/reporting/task/file/13022119904140051/20190813175743560/新建文本文档 (2).txt" ;
        String str2 = "http://127.0.0.1:8083/reporting/task/file/13022119904140051/20190813175743560/ss (2).txt" ;
        String str3 = "http://127.0.0.1:8083/reporting/task/file/13022119904140051/20190813175743560/ss.txt" ;

        String encode = URLUtil.encode(str);
        String encode2 = URLUtil.encode(str2);
        String encode3 = URLUtil.encode(str3);
        System.out.println(encode);
        System.out.println(encode2);
        System.out.println(encode3);
    }
}
