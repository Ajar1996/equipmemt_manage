package com.ajar.springbootshiro.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShrioConfig {


    @Bean("enterCacheSessionDAO")
    public EnterpriseCacheSessionDAO enterCacheSessionDAO() {
        EnterpriseCacheSessionDAO enterCacheSessionDAO = new EnterpriseCacheSessionDAO();
        //添加缓存管理器
        //enterCacheSessionDAO.setCacheManager(ehCacheManager());
        //添加ehcache活跃缓存名称（必须和ehcache缓存名称一致）
        //enterCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        return enterCacheSessionDAO;
    }

    @Bean("sessionManager")
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager=new DefaultWebSessionManager();
        sessionManager.setSessionDAO(enterCacheSessionDAO());
        //设置session的过期时间为1小时，(默认时间时30分钟)
        sessionManager.setGlobalSessionTimeout(60*60*1000);
        //开启扫描session线程，清理超时会话
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //禁用了url重写 去掉URL中的JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);//默认true
        return sessionManager;
    }

    /**
     * 创建SecurityManager
     */
    @Bean("securityManager")
    public SecurityManager securityManager(UserRealm userRealm, SessionManager sessionManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        securityManager.setRealm(userRealm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    /**
     * 创建shiroFilter过滤器
     * @param securityManager
     * @return
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        //anon：它对应的过滤器里面是空的,什么都没做,这里.do和.jsp后面的*表示参数,比方说login.jsp?main -->
        //authc：该过滤器下的页面必须验证后才能访问,它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setLoginUrl("/anno/notLogin");//没有认证时跳转到的登陆页
        //shiroFilter.setSuccessUrl("/index.html");//认证成功跳转到主页
        //shiroFilter.setUnauthorizedUrl("/unauthorized.html");//未授权时的跳转链接

        Map<String,String> filterMap = new LinkedHashMap<>();

        filterMap.put("/admin/**", "authc");
        filterMap.put("/anno/**", "anon");

        filterMap.put("/druid/**","anon");
        filterMap.put("/public/**","anon"); //放行静态资源的路径
        filterMap.put("/login.html","anon");
        filterMap.put("/sys/login","anon");
        filterMap.put("/captcha.jpg","anon");//验证码的图片
        //filterMap.put("/**","authc");//authc经过认证才能访问

        //角色验证 具有admin角色的用户可以访问
        //filterMap.put("/sys/menu/del","roles[admin]");
        //权限验证 具有perms[sys:menu:update]可以访问
        //filterMap.put("/sys/menu/update","perms[sys:menu:update]");

        filterMap.put("/**","user");//通过记住我访问

        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

    /**
     * ShiroConfig配置文件中开启注解
     * 配置三个bean：
     * lifecycleBeanPostProcessor
     * defaultAdvisorAutoProxyCreator
     * authorizationAttributeSourceAdvisor
     */

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }





}
