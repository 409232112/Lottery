package com.wyc.core.shiro.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wangyc on 2019/11/11.
 */
@Configuration
public class ShiroConfig {

    private static String prefix;
    @Value("${visit.prefix}")
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl(prefix+"/login");
        shiroFilterFactoryBean.setSuccessUrl("/main.html");

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问

        // 静态资源可以匿名访问
        filterChainDefinitionMap.put("/favicon.ico","anon");

        filterChainDefinitionMap.put("/images/**","anon");
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");

        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/logout", "anon");
        filterChainDefinitionMap.put("/identifyCode", "anon");

        filterChainDefinitionMap.put("/files/**", "anon");

        
        filterChainDefinitionMap.put("/LotteryMgr/LotteryUser.html", "anon");
        filterChainDefinitionMap.put("/LotteryMgr/lottery/LotteryUser", "anon");
        filterChainDefinitionMap.put("/LotteryMgr/lottery/lotteryData/**","anon");
        filterChainDefinitionMap.put("/LotteryMgr/css/**", "anon");
        filterChainDefinitionMap.put("/LotteryMgr/js/**", "anon");
        filterChainDefinitionMap.put("/LotteryMgr/images/**", "anon");






        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截 剩余的都需要认证
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;

    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(customRealm());
        return defaultSecurityManager;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }


    @Bean
    public CustomRealm customRealm() {
        CustomRealm customRealm = new CustomRealm();
        return customRealm;
    }
}
