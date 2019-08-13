package com.telecomyt.item.web.controller;

import com.telecomyt.item.exception.BasicException;
import com.telecomyt.item.utils.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
public class FileController {

    @GetMapping("/download")
    public void test(@RequestParam("url")  String url ,HttpServletResponse response ){
        boolean result = FileUtil.downLoadFile(url, response);
        if(!result){
            throw new BasicException(400,"下载错误");
        }
    }


}
