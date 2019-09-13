package com.qbk.web.controller.user;

import com.qbk.constant.CommonConstant;
import com.qbk.entity.Role;
import com.qbk.entity.User;
import com.qbk.entity.param.LoginRequest;
import com.qbk.entity.param.UserAddParam;
import com.qbk.log.annotation.ServiceLog;
import com.qbk.result.BaseResult;
import com.qbk.result.BaseResultGenerator;
import com.qbk.shiro.JWTToken;
import com.qbk.shiro.JWTUtil;
import com.qbk.shiro.ShiroUtil;
import com.qbk.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
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
import java.util.Set;

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


    //TODO 修改用户时候需要版本号 还需要修改密码的版本号，用户校验token时 是否密码修改过

    //TODO 删除用户的时候 需要把登陆名称改掉 因为登陆名是唯一索引

    /**
     * 查询用户列表
     * 权限 ：admin  or   user
     *
     *  Logical.OR 表示或者关系，默认是  Logical.AND ，如果是AND 需要当前登陆的用户同时满足所有角色才可以
     */
    @RequiresRoles(value={ "user"} )
    @GetMapping("/select/list")
    public BaseResult<String> selectList() {
        // shiro获取当前登录的用户信息,username 是过滤器中校验token时放进去的
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        System.out.println(username);

        subject.checkRole("user");


//        int currentPage = 1;
//        int pageSize = 5;
//        String orderBy = "id desc";
//        int count = userMapper.selectCount(null);
//        PageHelper.startPage(currentPage, pageSize, orderBy);
//        List<User> users = userMapper.selectAll();
//        PageInfo<User> userPageInfo = new PageInfo<>(users);
//        Assert.assertEquals(5, userPageInfo.getSize());
//        Assert.assertEquals(count, userPageInfo.getTotal());
//        log.debug("【userPageInfo】= {}", userPageInfo);
        return BaseResultGenerator.success("成功");
    }
}
