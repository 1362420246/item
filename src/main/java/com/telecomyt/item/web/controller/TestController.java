package com.telecomyt.item.web.controller;

import com.telecomyt.item.enums.ResultStatus;
import com.telecomyt.item.dto.BaseResp;
import com.telecomyt.item.web.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/test")
@RestController
public class TestController {

    @Autowired
    private TestService testService ;

    @GetMapping("/get")
    public BaseResp<String> test(){
        String test = testService.test();
        System.out.println(test);
        log.info("测试");
        return new BaseResp<>(ResultStatus.SUCCESS);
    }

}
