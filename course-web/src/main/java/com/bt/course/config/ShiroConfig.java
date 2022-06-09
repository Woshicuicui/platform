package com.bt.course.config;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authz.SslFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 定义权限，加载拦截器
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 填充filter过滤规则
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("ssl", new SslFilter());
//        配置规则，名称为jwt
        filterMap.put("jwt", new JWTFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        // 设置login地址
        shiroFilterFactoryBean.setLoginUrl("/course/login");
        // 设置安全事务管理器
        shiroFilterFactoryBean.setSecurityManager(manager);
        // 设置没有登录的地址
        shiroFilterFactoryBean.setUnauthorizedUrl("/course/401");

        // 设定拦截的规则，
        LinkedHashMap<String, String> filterRules = new LinkedHashMap<>();
        // 用anon标记规则的URI不会被拦截
        // 如果你需要模糊匹配，用**
        filterRules.put("/swagger**", "anon");
        filterRules.put("/swagger-ui/**", "anon");
        //没用这行swagger打不开
        filterRules.put("/webjars/**", "anon");
        filterRules.put("/v**/api-docs", "anon");
        filterRules.put("/swagger-resources/configuration/ui", "anon");
        // 把我们的登录加到过滤规则里，不然登录都会被提示401
        filterRules.put("/api/auth/tokens", "anon");
        filterRules.put("/api/auth/regist", "anon");
        filterRules.put("/api/auth/401", "anon");

        // 设定需要拦截的规则
        // 此处等于所有接口都会被拦截，拦截使用规则为 jwt规则，
        filterRules.put("/**", "jwt");
        // 设置规则
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterRules);
        return shiroFilterFactoryBean;
    }

    // 配置核心安全事务管理器
    @Bean(name = "securityManager")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(jwtRealm());
        DefaultSubjectDAO defaultSubjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        defaultSubjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(defaultSubjectDAO);
        return manager;
    }

    // 自定义的权限登录器
    @Bean(name = "jwtRealm")
    public JWTRealm jwtRealm() {
        return new JWTRealm();
    }


}
