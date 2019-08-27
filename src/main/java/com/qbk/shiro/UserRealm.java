package com.qbk.shiro;

import com.qbk.entity.Role;
import com.qbk.entity.User;
import com.qbk.log.annotation.ServiceLog;
import com.qbk.web.mapper.UserMapper;
import com.qbk.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 认证 & 授权
 * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
 */
@Slf4j
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;

    /**
     * 校验Token类型
     * 必须重写此方法，不然会报错
     * UsernamePasswordToken 用于登陆校验
     * JWTToken 用于校验token
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken || token instanceof UsernamePasswordToken;
    }

    /**
     * 授权(验证权限时调用)
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = principals.toString();
        //获得该用户角色
        List<Role> roleList = userMapper.getRoleByUserName(username);
        Set<Integer> roleIds = roleList.stream().map(Role::getId).collect(Collectors.toSet());
        Set<String> roleNames = roleList.stream().map(Role::getName).collect(Collectors.toSet());
        //获得该用户的权限
        List<String> menuNames = userMapper.getMenuNameByRoleIds(roleIds);
        //需要将 role, permission 封装到 Set 作为 info.setRoles(), info.setStringPermissions() 的参数
        //设置该用户拥有的角色和权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roleNames);
        info.setStringPermissions(new HashSet<>(menuNames));
        return info;
    }

    /**
     * 认证(登录时调用)
     * 获取身份验证信息
     *
     * @param authcToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        String username = (String) authcToken.getPrincipal();
        User user = userMapper.getUserByName(username);
        if (user == null) {
            log.debug("{}该用户不存在！",username);
            throw new UnknownAccountException();
        }
        if (user.getIsLock()) {
            log.debug("{}该用户已被封号",username);
            throw new LockedAccountException("该用户已被封号！");
        }
        /**
         * 返回一个从数据库中查出来的的凭证。用户名为和密码。
         * 接下来shiro框架做的事情就很简单了。
         * 它会拿你的输入的token与当前返回的这个数据库凭证SimpleAuthenticationInfo对比一下
         * 看看是不是一样，如果用户的帐号密码与数据库中查出来的数据一样，那么本次登录成功
         * 否则就是你密码输入错误
         */
        if(authcToken instanceof JWTToken ){
            return new SimpleAuthenticationInfo(user.getLoginName(), username , getName());
        }else {
            return new SimpleAuthenticationInfo(user.getLoginName(), user.getPassword() ,ByteSource.Util.bytes(user.getSalt()), getName());
        }
    }

    /**
     * 注入父类的属性，注入加密算法匹配密码时使用
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName(ShiroKit.HASH_ALGORITHM_NAME);
        shaCredentialsMatcher.setHashIterations(ShiroKit.HASH_ITERATIONS);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }
}

