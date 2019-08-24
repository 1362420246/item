package com.qbk.web.controller;

import com.qbk.exception.BasicException;
import com.qbk.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
public class FileController {

    /**
     * 下载
     */
    @GetMapping("/download")
    public void test(@RequestParam("url")String url ,HttpServletResponse response ){
        boolean result = FileUtil.downLoadFile(url, response);
        if(!result){
            throw new BasicException(400,"下载错误");
        }
    }

}
