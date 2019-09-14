package com.qbk.shiro;

import com.qbk.web.mapper.UserMapper;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AllSuccessfulStrategy;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

/**
 *  1. 首先需要提供一个 Realm 的实例
 *  2. 需要配置一个 SecurityManager，在 SecurityManager 中配置 Realm
 *  3. 配置一个 ShiroFilterFactoryBean ，在 ShiroFilterFactoryBean 中指定路径拦截规则等
 *
 *  参考：
 *  https://www.iteye.com/blog/jinnianshilongnian-2018398
 *  https://www.jianshu.com/p/0b1131be7ace
 */
@Configuration
public class ShiroConfig {

    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager(UserRealm userRealm ,UserJwtRealm userJwtRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        //系统自带的Realm管理，主要针对多realm 认证
        ModularRealmAuthenticator modularRealmAuthenticator = new ModularRealmAuthenticator();
        /*
         * FirstSuccessfulStrategy：只要有一个Realm验证成功即可，只返回第一个Realm身份验证成功的认证信息，其他的忽略；
         * AtLeastOneSuccessfulStrategy：只要有一个Realm验证成功即可，和FirstSuccessfulStrategy不同，返回所有Realm身份验证成功的认证信息；
         * AllSuccessfulStrategy：所有Realm验证成功才算成功，且返回所有Realm身份验证成功的认证信息，如果有一个失败就失败了。
         *  ModularRealmAuthenticator默认使用AtLeastOneSuccessfulStrategy策略。
         */
        modularRealmAuthenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        securityManager.setAuthenticator(modularRealmAuthenticator);

        //针对多realm 授权
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        //通过PermissionResolver将权限字符串转换成相应的Permission实例，默认使用WildcardPermissionResolver，即转换为通配符的WildcardPermission；
        authorizer.setPermissionResolver(new WildcardPermissionResolver());
        securityManager.setAuthorizer(authorizer);


        // 设置多个realm. 注意顺序，按照顺序执行
        List<Realm> realms = new ArrayList<>();
        realms.add(userRealm);
        realms.add(userJwtRealm);
        securityManager.setRealms(realms);

        // 关闭自带session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        /*
         * 禁用session, 不保存用户登录状态。保证每次请求都重新认证。
         * 需要注意的是，如果用户代码里调用Subject.getSession()还是可以用session，如果要完全禁用，要配合下面的noSessionCreation的Filter来实现
         */
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     *  初始化 ShiroFilterFactoryBean 的时候需要注入 SecurityManager
     *
     *  先走 filter ，然后 filter 如果检测到请求头存在 token，则用 token 去 login，走 Realm 去验证
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        //指定 SecurityManager
        shiroFilter.setSecurityManager(securityManager);

        //访问未获授权路径时跳转的url
        shiroFilter.setUnauthorizedUrl("/admin");

        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        //设置我们自定义的JWT过滤器
        filterMap.put("jwt", new JWTFilter());
        shiroFilter.setFilters(filterMap);

        //设置拦截器 配置路径拦截规则，注意，要有序
        Map<String, String> filterRuleMap = new LinkedHashMap<>();
        // 不通过JWTFilter
        filterRuleMap.put("/user/login", "anon");
        filterRuleMap.put("/test/connection", "anon");
        //swagger接口权限 开放
        filterRuleMap.put("/swagger-ui.html", "anon");
        filterRuleMap.put("/v2/api-docs", "anon");
        filterRuleMap.put("/configuration/security", "anon");
        filterRuleMap.put("/configuration/ui", "anon");
        filterRuleMap.put("/swagger-resources", "anon");
        filterRuleMap.put("/favicon.ico", "anon");
        filterRuleMap.put("/v3/api-docs", "anon");
        filterRuleMap.put("/v3/swagger-security", "anon");
        filterRuleMap.put("/v3/swagger-login", "anon");
        filterRuleMap.put("/static/**", "anon");

        // 所有请求通过我们自己的JWT Filter 。这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        // 指定为 noSessionCreationFilter 过滤器 ，用途 禁用session
        filterRuleMap.put("/**", "noSessionCreation,jwt");
        shiroFilter.setFilterChainDefinitionMap(filterRuleMap);
        return shiroFilter;
    }

    /**
     * 开启shiro aop注解支持
     * 注解访问授权动态拦截，不然不会执行doGetAuthenticationInfo
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
