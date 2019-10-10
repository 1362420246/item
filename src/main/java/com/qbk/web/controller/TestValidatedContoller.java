package com.qbk.web.controller;

import com.qbk.entity.param.TestResponse;
import com.qbk.log.annotation.ServiceLog;
import com.qbk.result.BaseResult;
import com.qbk.result.BaseResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author ：quboka
 * @description：校验测试
 * @date ：2019/10/10 12:10
 */
@Slf4j
@RestController
@RequestMapping("/test")
@Profile({ "dev"})
@Api(tags = "1.5",value = "校验测试控制器",description="校验测试控制器")
@Validated//需要放到类上
public class TestValidatedContoller {

    /**
     * 校验测试六：
     * 1.使用 @Validated 校验多个参数
     */
    @ApiOperation(value = "校验测试6", notes = "使用 @Validated 校验多个参数")
    @ServiceLog("参数校验测试6")
    @GetMapping("/v1.6/param_test")
    public BaseResult<TestResponse> parameterValidated(
            @Size(min = 5,message = "不能少于5") String name ,
            @NotNull(message = "不能为空") String username ){
        log.debug("接收的参数是:{},{}",name,username);
        return BaseResultGenerator.success();
    }

}

