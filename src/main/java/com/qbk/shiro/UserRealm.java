package com.qbk.shiro;

import com.qbk.entity.AuthorizationUser;
import com.qbk.entity.Role;
import com.qbk.entity.User;
import com.qbk.log.annotation.ServiceLog;
import com.qbk.web.mapper.UserMapper;
import com.qbk.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
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
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 授权(验证权限时调用)
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        /*
         * PrincipalCollection 一个身份集合，其包含了Realm验证成功的身份信息
               类型 为 doGetAuthenticationInfo() 方法返回值 SimpleAuthenticationInfo 的第一个参数类型
               通常 为 User实体 或者 username（String）
         * Object getPrimaryPrincipal()  如果只有一个Principal 那么直接返回即可，
              如果有多个Principal，则返回第一个（因为内部使用Map存储，所以可以认为是返回任意一个）；
         * T oneByType(Class<T> type) 根据凭据的类型返回相应的Principal；
         * Collection fromRealm(String realmName)  根据Realm 名字（每个Principal 都与一个Realm 关联）获取相应的Principal。
         */
        Object primaryPrincipal = principals.getPrimaryPrincipal();
        AuthorizationUser uesr = (AuthorizationUser)primaryPrincipal;
        String username = uesr.getLoginName();
        //获得该用户角色
        List<Role> roleList = userMapper.getRoleByUserName(username);
        Set<Integer> roleIds = roleList.stream().map(Role::getId).collect(Collectors.toSet());
        Set<String> roleNames = roleList.stream().map(Role::getName).collect(Collectors.toSet());
        //获得该用户的权限
        List<String> menuNames = userMapper.getMenuNameByRoleIds(roleIds);
        HashSet<String> menuNameSet = new HashSet<>(menuNames);
        //需要将 role, permission 封装到 Set 作为 info.setRoles(), info.setStringPermissions() 的参数
        //设置该用户拥有的角色和权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roleNames);
        info.setStringPermissions(menuNameSet);

        //存储角色权限， 方便subject.getPrincipal() 时获取的对象包含角色权限
        uesr.setRoleNames(roleNames);
        uesr.setMenuNames(menuNameSet);
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
        /*登陆token
         * 返回一个从数据库中查出来的的凭证。用户名为和密码。
         * 接下来shiro框架做的事情就很简单了。
         * 它会拿你的输入的token与当前返回的这个数据库凭证SimpleAuthenticationInfo对比一下
         * 看看是不是一样，如果用户的帐号密码与数据库中查出来的数据一样，那么本次登录成功
         * 否则就是你密码输入错误
        * @param 主体与指定域关联的“主”主体。
        * @param 拥有验证给定主体的散列凭证。
        * @param credentialsSalt散列给定的hashedCredentials时使用的salt
        * @param realmName获取主体和凭据的领域
         */
        return new SimpleAuthenticationInfo(
                AuthorizationUser.copyProperties(user),
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                getName());
    }

    /**
     * 注入父类的属性，注入加密算法和循环次数 匹配密码时使用
     *  UsernamePasswordToken token = new UsernamePasswordToken(username, password); 时
        password 就会被下面算法 和循环次数 加密。
        对应SimpleAuthenticationInfo(Object principal, Object hashedCredentials, ByteSource credentialsSalt, String realmName)
        中的credentialsSalt 盐值加密后 和 hashedCredentials 对比
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        //加密算法
        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtil.HASH_ALGORITHM_NAME);
        //循环次数
        shaCredentialsMatcher.setHashIterations(ShiroUtil.HASH_ITERATIONS);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }
}

