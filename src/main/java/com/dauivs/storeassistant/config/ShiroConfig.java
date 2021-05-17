package com.dauivs.storeassistant.config;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.tomcat.websocket.AuthenticatorFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    /**
     * 加密算法
     */
    public static final String HASH_ALGORITHM_NAME = "MD5";

    /**
     * 加密次数
     */
    public static final int HASH_ITERATIONS = 5;

    /**
     * 加密盐
     */
    public static final String HASH_SALT = "salt";

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, ShiroAccessFilter shiroAccessFilter) {
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("access", shiroAccessFilter);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/sys/user/logout", "logout");
        filterChainDefinitionMap.put("/sys/user/login", "anon");
        filterChainDefinitionMap.put("/**", "access");

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setFilters(filters);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(ShiroRealm shiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm);
        return securityManager;
    }

    @Bean
    public ShiroRealm shiroRealm(CredentialsMatcher credentialsMatcher) {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(credentialsMatcher);
        return shiroRealm;
    }

    @Bean
    public CredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(HASH_ALGORITHM_NAME);
        credentialsMatcher.setHashIterations(HASH_ITERATIONS);
        return credentialsMatcher;
    }

    @Bean
    public ShiroAccessFilter shiroAccessFilter() {
        return new ShiroAccessFilter();
    }

    @Bean
    public FilterRegistrationBean shiroAccessFilterRegistration(ShiroAccessFilter shiroAccessFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(shiroAccessFilter);
        registration.setEnabled(false);
        return registration;
    }

}
