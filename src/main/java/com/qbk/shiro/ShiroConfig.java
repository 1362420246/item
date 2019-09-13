package com.qbk.shiro;

import com.qbk.web.mapper.UserMapper;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  1. 首先需要提供一个 Realm 的实例
 *  2. 需要配置一个 SecurityManager，在 SecurityManager 中配置 Realm
 *  3. 配置一个 ShiroFilterFactoryBean ，在 ShiroFilterFactoryBean 中指定路径拦截规则等
 */
@Configuration
public class ShiroConfig {

    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(userRealm);
        // 关闭自带session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
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
        filterRuleMap.put("/**", "jwt");
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
