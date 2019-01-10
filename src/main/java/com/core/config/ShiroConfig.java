package com.core.config;

import com.base.filter.RoleAnyFilter;
import com.core.shiro.MyRealm;
import com.core.shiro.cacheManage.RedisCache;
import com.core.shiro.cacheManage.RedisCacheManager;
import com.core.shiro.sessionManage.CustomSessionManage;
import com.core.shiro.sessionManage.RedisSessionDao;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 */
@Configuration
public class ShiroConfig {

    // 用户登录验证失败后跳转的URL
    @Value("${website.redirectUri}")
    private String redirectUri;

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        return new JedisPoolConfig();
    }

    @Bean
    public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig) {
        return new JedisPool(jedisPoolConfig, "127.0.0.1", 6379);
    }


    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(1);
        return hashedCredentialsMatcher;
    }

    @Bean
    public Realm realm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        MyRealm myRealm = new MyRealm();
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return myRealm;
    }

    /**
     * 重写session存储方法
     * @return
     */
    @Bean
    public SessionDAO sessionDAO() {
        return new RedisSessionDao();
    }

    /**
     * 自定义session管理器
     * 优化shiro多次调用redis
     *
     * @param sessionDAO
     * @return
     */
    @Bean
    public CustomSessionManage customSessionManage(SessionDAO sessionDAO) {
        CustomSessionManage customSessionManage = new CustomSessionManage();
        customSessionManage.setSessionDAO(sessionDAO);
        return customSessionManage;
    }

    @Bean
    public RedisCache redisCache() {
        return new RedisCache();
    }

    @Bean
    public CacheManager redisCacheManager() {
        return new RedisCacheManager();
    }

    @Bean
    public SimpleCookie simpleCookie() {
        //name必须要和登陆页'记住我'复选框的name保持一致
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //记住我cookie生效时间30天 ,单位秒
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    @Bean
    public CookieRememberMeManager cookieRememberMeManager(SimpleCookie simpleCookie) {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(simpleCookie);
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

    @Bean
    public SecurityManager securityManager(Realm realm, CustomSessionManage customSessionManage, CacheManager redisCacheManager, CookieRememberMeManager cookieRememberMeManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setSessionManager(customSessionManage);
        securityManager.setCacheManager(redisCacheManager);
        securityManager.setRememberMeManager(cookieRememberMeManager);
        return securityManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl(getRedirectUri());//登陆链接
        Map<String, String> map = new HashMap<>();
        map.put("/notice/addNotice", "roleAny[\"admin\",\"user\"]");//必须拥有admin或user之中的一个角色
        map.put("/web/redPacket", "roleAny[\"admin\",\"user\"]");//必须拥有admin或user之中的一个角色
        map.put("/web/doRedPacket", "roleAny[\"admin\",\"user\"]");//必须拥有admin或user之中的一个角色
        map.put("/user/logout", "logout");//注销
        map.put("/*", "anon");//匿名使用
        //方式一：在方法中使用：
        //subject.isPermitted("user:update")) 判断用户是否具有用户更新权限
        //subject.hasRole("admin")) {//判断用户是否拥有admin角色
        //方式二：在方法前添加注解：
        //@RequiresRoles("admin") 判断用户是否拥有admin角色
        //@RequiresPermissions({"user:update","user:delete"}) 判断是否具有用户更新和删除权限
        //方式三：在shiroFolter配置中：
        //map.put("/web/manage", "authc");//必须登陆才能使用
        //map.put("/essay/manage", "perms['essay:update','essay:publish']");//必须同时拥有文章更新和文章推送的权限
        //map.put("/picture/manage", "roles['admin','user']");//必须拥有admin和user角色
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        Map<String, Filter> filters = new HashMap<>();
        filters.put("roleAny", new RoleAnyFilter());//自定义角色拦截器
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }

    /***
     * 授权注解
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * Shiro生命周期处理器
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public FilterRegistrationBean delegatingFilterProxy() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }
}
