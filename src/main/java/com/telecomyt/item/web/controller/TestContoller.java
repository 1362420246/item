package com.telecomyt.item.web.controller;

import com.telecomyt.item.dto.resp.BaseResp;
import com.telecomyt.item.enums.ResultStatus;
import com.telecomyt.item.web.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 */
@RestController
@RequestMapping("/test")
@Profile({ "dev"})
public class TestContoller {

    @Autowired
    private TestService testService ;

    @GetMapping("/get")
    public BaseResp<String> test(){
        testService.testConnection();
        return new BaseResp<>(ResultStatus.SUCCESS,"test");
    }
}
