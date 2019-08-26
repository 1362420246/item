package com.qbk.web.controller.user;

import com.qbk.log.annotation.ServiceLog;
import com.qbk.result.BaseResult;
import com.qbk.result.BaseResultGenerator;
import com.qbk.shiro.JWTToken;
import com.qbk.shiro.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

@Api(tags = "1.0.0", value = "用户控制器")
@RestController
@RequestMapping("/user")
public class UserController {


    @ApiOperation(value = "登陆", notes = "用户登陆")
//    @ServiceLog("登陆")
    @PostMapping("/login")
    public BaseResult<String> login(@RequestParam("username") String username ,
                                    @RequestParam("password") String password ){
        try {
            Subject subject = SecurityUtils.getSubject();
            //将用户请求参数封装后，直接提交给Shiro处理
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
            boolean authenticated = subject.isAuthenticated();
            System.out.println("是否认证成功："+authenticated);
            return BaseResultGenerator.success("登陆成功",JWTUtil.createToken(username));
        } catch (UnknownAccountException e) {
            return BaseResultGenerator.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return BaseResultGenerator.error("账号或密码不正确");
        } catch (LockedAccountException e) {
            return BaseResultGenerator.error("账号已被锁定,请联系管理员");
        } catch (AuthenticationException e) {
            return BaseResultGenerator.error("账户验证失败");
        }
    }

}
