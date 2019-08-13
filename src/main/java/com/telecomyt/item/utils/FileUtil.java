package com.telecomyt.item.utils;

import com.telecomyt.item.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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
     * 下载
     */
    public static boolean downLoadFile(String filePath , HttpServletResponse response ) {
        if(filePath == null){
           return false;
        }
        BufferedOutputStream output = null;
        try {
            URL url = new URL(filePath);
            String path = url.getPath();
            String fileName = path.substring(path.lastIndexOf("/") + 1);
            filePath = encodeURLChinese(filePath);
            if(filePath == null){
                return false;
            }
            byte[] bytes = IOUtils.toByteArray(new URL(filePath));
            response.setContentType("multipart/form-data;charset=GBK");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1") );
            output = new BufferedOutputStream(response.getOutputStream());
            output.write(bytes);
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


    /**
     * 获取按要求编码后的URL列表
     *
     * @param url
     * @return
     */
    public static String encodeURLChinese(String url) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        url = StringUtils.trim(url);
        try {
            if (!needEncoding(url)) {
                // 不需要编码
                return url;
            } else {
                // 需要编码

                String allowChars = ".!*'();:@&=+_\\-$,/?#\\[\\]{}|\\^~`<>%\"";
//              String  allowChars = ".!*'();:@&=+_\\-$,/?#\\[\\]{}|\\^~`<>%\"";
                // UTF-8 大写
                return encode(url, "UTF-8", allowChars, false);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断一个url是否需要编码，按需要增减过滤的字符
     *
     * @param url
     * @return
     */
    public static boolean needEncoding(String url) {
        // 不需要编码的正则表达式
//      String allowChars = SystemConfig.getValue("ENCODING_ALLOW_REGEX",
//              Constants.ENCODING_ALLOW_REGEX);
        if (url.matches("^[0-9a-zA-Z.:/?=&%~`#()-+]+$")) {
            return false;
        }

        return true;
    }
    /**
     * 对字符串中的特定字符进行编码
     *
     * @param s
     *            待编码的字符串
     * @param enc
     *            编码类型
     * @param allowed
     *            不需要编码的字符
     * @param lowerCase
     *            true:小写 false：大写
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public static final String encode(String s, String enc, String allowed,
                                      boolean lowerCase) throws UnsupportedEncodingException {

        byte[] bytes = s.getBytes(enc);
        int count = bytes.length;

        /*
         * From RFC 2396:
         *
         * mark = "-" | "_" | "." | "!" | "~" | "*" | "'" | "(" | ")" reserved =
         * ";" | "/" | ":" | "?" | "@" | "&" | "=" | "+" | "$" | ","
         */
        // final String allowed = "=,+;.'-@&/$_()!~*:"; // '?' is omitted
        char[] buf = new char[3 * count];
        int j = 0;

        for (int i = 0; i < count; i++) {
            if ((bytes[i] >= 0x61 && bytes[i] <= 0x7A) || // a..z
                    (bytes[i] >= 0x41 && bytes[i] <= 0x5A) || // A..Z
                    (bytes[i] >= 0x30 && bytes[i] <= 0x39) || // 0..9
                    (allowed.indexOf(bytes[i]) >= 0)) {
                buf[j++] = (char) bytes[i];
            } else {
                buf[j++] = '%';
                if (lowerCase) {
                    buf[j++] = Character.forDigit(0xF & (bytes[i] >>> 4), 16);
                    buf[j++] = Character.forDigit(0xF & bytes[i], 16);
                } else {
                    buf[j++] = lowerCaseToUpperCase(Character.forDigit(
                            0xF & (bytes[i] >>> 4), 16));
                    buf[j++] = lowerCaseToUpperCase(Character.forDigit(
                            0xF & bytes[i], 16));
                }

            }
        }
        return new String(buf, 0, j);
    }

    public static char lowerCaseToUpperCase(char ch) {
        if (ch >= 97 && ch <= 122) { // 如果是小写字母就转化成大写字母
            ch = (char) (ch - 32);
        }
        return ch;
    }


    public static void main(String[] args) throws IOException {
        String str = "http://127.0.0.1:8083/reporting/task/file/13022119904140051/20190813175743560/新建文本文档.txt" ;
        String s = encodeURLChinese(str);
        System.out.println(s);
        String substring = str.substring(str.lastIndexOf("."));
        System.out.println(substring);
        URL url = new URL(str);
        String path = url.getPath();
        System.out.println(path);
        System.out.println(path.substring(path.lastIndexOf("/") + 1));
    }
}
