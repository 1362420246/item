package com.qbk.shiro;

import com.qbk.entity.AuthorizationUser;
import com.qbk.entity.Role;
import com.qbk.entity.User;
import com.qbk.web.mapper.UserMapper;
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
 * jwtRealm
 */
@Slf4j
@Component("userJwtRealm")
public class UserJwtRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken ;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
      return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        User user = userMapper.getUserByName(username);
        if (user == null) {
            log.debug("{}该用户不存在！", username);
            throw new UnknownAccountException();
        }
        if (user.getIsLock()) {
            log.debug("{}该用户已被封号", username);
            throw new LockedAccountException("该用户已被封号！");
        }

          /*校验token
           * @param 可以是username 也可以是user实体，但是取的时候要注意类型
           *        和token中getPrincipal()对应
           * @param 密码
           *        和token中getCredentials()对应
           * @param 盐值
           * @param 当前的realm名
        */
//        return new SimpleAuthenticationInfo(
//                AuthorizationUser.copyProperties(user) ,
//                user.getLoginName(),
//                ByteSource.Util.bytes(user.getSalt()) ,
//                "userJwtRealm");

         /*校验token
           * @param user
           * @param JWTToken中的getCredentials()
           * @param 当前的realm名
        */
        return new SimpleAuthenticationInfo(
                AuthorizationUser.copyProperties(user) ,
                user.getLoginName(),
                getName());
    }

}
