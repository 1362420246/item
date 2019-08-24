package com.qbk.web.controller;

import com.qbk.log.annotation.ServiceLog;
import com.qbk.entity.converter.TestConverterMapper;
import com.qbk.entity.param.TestRequest;
import com.qbk.entity.param.TestResponse;
import com.qbk.entity.valid.BeanValidator;
import com.qbk.entity.valid.ValidableList;
import com.qbk.result.BaseResult;
import com.qbk.result.BaseResultGenerator;
import com.qbk.web.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 测试接口
 */
@Slf4j
@RestController
@RequestMapping("/test")
@Profile({ "dev"})
public class TestContoller {

    @Autowired
    private TestService testService ;

    /**
     * 数据库连接测试：
     * 分别测试有没有事务的时候，数据库连接是否是同一个
     * 需要并发测试才能看到结果
     */
    @ServiceLog("测试连接")
    @GetMapping("/connection")
    public BaseResult<String> testConnection(){
        testService.testConnection();
        return BaseResultGenerator.success("test");
    }

    /**
     * 校验测试一：
     * 1.使用注解 @Valid 校验单个对象
     * 2.转换单个对象
     */
    @ServiceLog("参数校验测试1")
    @PostMapping("/v1.1/param_test")
    public BaseResult<TestResponse> parameterCalibration(@Valid @RequestBody TestRequest testRequest){
        log.debug("接收的参数是:{}",testRequest);
        //对象转换
        TestResponse converter = TestConverterMapper.INSTANCE.converter(testRequest);
        return BaseResultGenerator.success(converter);
    }

    /**
     * 校验测试二：
     * 1.使用注解 @Valid 和ValidableList 校验多个对象
     * 2.转换多个对象
     */
    @ServiceLog("参数校验测试2")
    @PostMapping("/v1.2/param_test")
    public BaseResult<List<TestResponse>> parameterCalibration(
            @Valid @RequestBody ValidableList<TestRequest> validableList){
        //使用校验list 需要把list取出
        List<TestRequest> testRequests = validableList.getList();
        log.debug("接收的参数是:{}",testRequests);
        //对象转换
        List<TestResponse> converter = TestConverterMapper.INSTANCE.converter(testRequests);
        return BaseResultGenerator.success(converter);
    }

    /**
     * 校验测试三：
     * 1.使用注解 工具类 BeanValidator 校验器 校验单个对象
     * 2.转换单个对象
     */
    @ServiceLog("参数校验测试3")
    @PostMapping("/v1.3/param_test")
    public BaseResult<TestResponse> parameterValidator(@RequestBody TestRequest testRequest){
        log.debug("接收的参数是:{}",testRequest);
        //校验
        BeanValidator.check(testRequest);
        //对象转换
        TestResponse converter = TestConverterMapper.INSTANCE.converter(testRequest);
        return BaseResultGenerator.success(converter);
    }


    /**
     * 校验测试四：
     * 1.使用注解 工具类 BeanValidator 校验器 校验多个对象
     * 2.转换多个对象
     */
    @ServiceLog("参数校验测试4")
    @PostMapping("/v1.4/param_test")
    public BaseResult<List<TestResponse>> parameterValidator(@RequestBody List<TestRequest> testRequests){
        log.debug("接收的参数是:{}",testRequests);
        //校验
        BeanValidator.check(testRequests);
        //对象转换
        List<TestResponse> converter = TestConverterMapper.INSTANCE.converter(testRequests);
        return BaseResultGenerator.success(converter);
    }
}