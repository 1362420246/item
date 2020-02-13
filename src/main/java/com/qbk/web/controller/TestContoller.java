package com.qbk.web.controller;

import com.battcn.boot.swagger.model.DataType;
import com.battcn.boot.swagger.model.ParamType;
import com.qbk.log.annotation.ServiceLog;
import com.qbk.entity.converter.TestConverterMapper;
import com.qbk.entity.param.TestRequest;
import com.qbk.entity.param.TestResponse;
import com.qbk.entity.valid.BeanValidator;
import com.qbk.entity.valid.ValidableList;
import com.qbk.result.BaseResult;
import com.qbk.result.BaseResultGenerator;
import com.qbk.web.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 测试接口
 */
@Slf4j
@RestController
@RequestMapping("/test")
@Profile({ "dev"})
@Api(tags = "1.4",value = "测试控制器",description="测试控制器")
public class TestContoller {

    @Autowired
    private TestService testService ;

    /**
     *  测试角色
     */
    @ApiOperation(value = "测试权限1", notes = "测试权限1")
    @ServiceLog("测试权限1")
    @GetMapping("/role/v1")
    public BaseResult<String> testRole1(){
        Subject subject = SecurityUtils.getSubject();
        subject.checkRole("admin");
        log.debug("测试权限1成功");
        return BaseResultGenerator.success("test");
    }

    /**
     * 测试注解角色
     * logical 如果指定了多个角色，则用于权限检查的逻辑操作,默认值 and
     */
    @RequiresRoles(value={"admin"},logical = Logical.OR)
    @ApiOperation(value = "测试权限2", notes = "测试权限2")
    @ServiceLog("测试权限2")
    @GetMapping("/role/v2")
    public BaseResult<String> testRole2(){
        log.debug("测试权限2成功");
        return BaseResultGenerator.success("test");
    }

    /**
     *  测试注解权限
     */
    @RequiresPermissions(value = {"测试操作"})
    @ApiOperation(value = "测试权限3", notes = "测试权限3")
    @ServiceLog("测试权限3")
    @GetMapping("/role/v3")
    public BaseResult<String> testPermission(){
        log.debug("测试权限3成功");
        return BaseResultGenerator.success("test");
    }

    /**
     * 数据库连接测试：
     * 分别测试有没有事务的时候，数据库连接是否是同一个
     * 需要并发测试才能看到结果
     */
    @ApiOperation(value = "测试连接", notes = "数据库连接测试")
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
    @ApiOperation(value = "校验测试1", notes = "使用注解 @Valid 校验单个对象")
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
    @ApiOperation(value = "校验测试2", notes = "使用注解 @Valid 和ValidableList 校验多个对象")
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
     * 1.使用工具类 BeanValidator 校验器 校验单个对象
     * 2.转换单个对象
     */
    @ApiOperation(value = "校验测试3", notes = "使用工具类 BeanValidator 校验器 校验单个对象")
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
     * 1.使用工具类 BeanValidator 校验器 校验多个对象
     * 2.转换多个对象
     */
    @ApiOperation(value = "校验测试4", notes = "使用工具类 BeanValidator 校验器 校验多个对象")
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

    /**
     * 校验测试五：
     * 1.使用 @Validated 校验单个对象
     */
    @ApiOperation(value = "校验测试5", notes = "使用 @Validated 校验单个对象")
    @ServiceLog("参数校验测试5")
    @GetMapping("/v1.5/param_test")
    public BaseResult<TestResponse> parameterValidated(@Validated TestRequest testRequest){
        log.debug("接收的参数是:{}",testRequest);
        //对象转换
        TestResponse converter = TestConverterMapper.INSTANCE.converter(testRequest);
        return BaseResultGenerator.success(converter);
    }

    /**
     * 校验测试七： 失败
     * 1.使用注解 @Valid 校验多个参数
     * 2.转换单个对象
     */
    @ApiOperation(value = "校验测试7", notes = "使用注解 @Valid 校验多个参数")
    @ServiceLog("参数校验测试7")
    @GetMapping("/v1.7/param_test")
    @Valid //TODO @Valid 注解放到类上方法上字段上都不能起到校验作用，这种散装校验还是需要用@Validated放到类上
    public BaseResult<?> parameterValids(
             @NotNull(message = "id不能为空") Integer id,
             @NotBlank(message = "name不能为空") String name
    ){
        System.out.println(id);
        System.out.println(name);
        return BaseResultGenerator.success();
    }
}
