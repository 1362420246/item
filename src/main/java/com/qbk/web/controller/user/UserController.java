package com.qbk.web.controller.user;

import com.battcn.boot.swagger.model.DataType;
import com.battcn.boot.swagger.model.ParamType;
import com.qbk.constant.CommonConstant;
import com.qbk.entity.AuthorizationUser;
import com.qbk.entity.Role;
import com.qbk.entity.User;
import com.qbk.entity.converter.UserDtoConverterMapper;
import com.qbk.entity.converter.UserDtoToUserVorMapper;
import com.qbk.entity.dto.UserDTO;
import com.qbk.entity.param.LoginRequest;
import com.qbk.entity.param.UserAddParam;
import com.qbk.entity.query.UserQuery;
import com.qbk.entity.vo.UserVo;
import com.qbk.log.annotation.ServiceLog;
import com.qbk.result.BaseResult;
import com.qbk.result.BaseResultGenerator;
import com.qbk.shiro.JWTToken;
import com.qbk.shiro.JWTUtil;
import com.qbk.shiro.ShiroUtil;
import com.qbk.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@Api(tags = "1.0.0", value = "用户控制器",description="用户控制器")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登陆
     * 权限：无需校验
     */
    @ApiOperation(value = "登陆", notes = "用户登陆")
    @ServiceLog("登陆")
    @PostMapping("/login")
    public BaseResult<String> login(@Valid @RequestBody LoginRequest loginRequest){
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        Subject subject = SecurityUtils.getSubject();
        //将用户请求参数封装token后，直接提交给Shiro处理，，触发认证方法
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
        return BaseResultGenerator.success("登陆成功",JWTUtil.createToken(username));
    }

    /**
     * 添加用户
     * 权限：管理员角色
     */
    @RequiresRoles(value={"admin"})
    @ApiOperation(value = "添加用户", notes = "添加用户")
    @ServiceLog("添加用户")
    @PostMapping("/add")
    public BaseResult<String> add(@Valid @RequestBody UserAddParam userAdd){
        User user = new User();
        BeanUtils.copyProperties(userAdd,user);
        //目前添加的用户只有一个角色
        user.setRoleList(Collections.singletonList(Role.builder().id(CommonConstant.ROLE_USER).build()));
        //获取盐值
        user.setSalt(ShiroUtil.getRandomSalt());
        //密码加密
        user.setPassword(ShiroUtil.sha256(user.getPassword(),user.getSalt()));
        return userService.add(user);
    }

    /**
     * 查询用户列表
     * 权限 ：admin  or   user
     *
     *  Logical.OR 表示或者关系，默认是  Logical.AND ，如果是AND 需要当前登陆的用户同时满足所有角色才可以
     *  如果value中有多个角色，那么会调用多次doGetAuthorizationInfo方法
     */
    @ServiceLog("查询用户列表")
    @ApiOperation(value = "查询用户列表", notes = "查询用户列表")
    @RequiresRoles(value={"user"} , logical = Logical.OR )
    @GetMapping("/select/list/{pageNum}/{pageSize}")
    @ApiImplicitParams({
               /* 参数描述
                name–参数名
                value–参数说明
                dataType–数据类型
                paramType–参数类型
                example–举例说明
             */
            @ApiImplicitParam(name = "pageNum", value = "页码",
            dataType = DataType.INT,required = true, paramType = ParamType.PATH, defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页大小",
            dataType = DataType.INT,required = true, paramType = ParamType.PATH, defaultValue = "2")
    })
    public BaseResult<List<UserVo>> selectList(@PathVariable int pageNum,@PathVariable int pageSize) {
        //Subject 存储在 ThreadLocal 中
        Subject subject = SecurityUtils.getSubject();
        // shiro获取当前登录的用户信息
        AuthorizationUser user = (AuthorizationUser) subject.getPrincipal();
        //查询参数
        UserQuery userQuery = new UserQuery();
        userQuery.setPageNum(pageNum);
        userQuery.setPageSize(pageSize);
        /*
          * 角色 分为 admin 和 user 。
          * 区别user 只能看非锁定用户，而 admin 可以查询所有
         */
        boolean isAdmin = user.getRoleNames().contains("admin");
        if(!isAdmin){
            //非admin用户只能看非锁定用户
            userQuery.setIsLock(false);
        }
        /*
            controller里处理参数 和 校验参数，
            因为service里面开启事务，事务可能会锁表，尽量把非事务逻辑在这层处理
         */
        List<UserDTO> userDTOList = userService.selectList(userQuery);
        //转换vo
        List<UserVo> userVos = UserDtoToUserVorMapper.INSTANCE.converter(userDTOList);
        return BaseResultGenerator.success("登陆成功",userVos);
    }

    //TODO 刷新token，因为jwt的token无法续时，只能给新的token

    //TODO 修改用户时候需要版本号 还需要修改密码的版本号，用户校验token时 是否密码修改过

    //TODO 删除用户的时候 需要把登陆名称改掉 因为登陆名是唯一索引

    //TODO  缓存
}
