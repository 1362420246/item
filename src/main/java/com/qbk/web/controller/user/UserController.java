package com.qbk.web.controller.user;

import com.qbk.log.annotation.ServiceLog;
import com.qbk.result.BaseResult;
import com.qbk.result.BaseResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "1.0.0", description = "用户控制器", value = "用户")
@RestController
@RequestMapping("/user")
public class UserController {

    //TODO
    @ApiOperation(value = "登陆", notes = "用户登陆")
    @ServiceLog("登陆")
    @GetMapping("/login")
    public BaseResult<String> login(){
        return BaseResultGenerator.success("user");
    }
}
