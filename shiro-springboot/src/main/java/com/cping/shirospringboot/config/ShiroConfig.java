package com.cping.shirospringboot.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /*第三步*/
    //ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        //添加shiro的内置过滤器
        /*  anon：无需认证就可以访问
        *   authc：必须认证了才能访问
        *   user：必须拥有 记住我 功能才能访问
        *   perms：拥有对某个资源的权限才能访问
        *   role：拥有某个角色权限才能访问
        * */
        //拦截
        Map<String, String> filterMap = new LinkedHashMap<>();

        //授权,正常的情况下，没有授权会跳转到未授权页面
        filterMap.put("/user/add", "perms[user:add]");
        filterMap.put("/user/update", "perms[user:update]");

        //向集合赋值
        /*filterMap.put("/user/add", "authc");
        filterMap.put("/user/update", "authc");*/

        /*filterMap.put("/user/*", "authc");*/
        bean.setFilterChainDefinitionMap(filterMap);

        //设置login路径
        bean.setLoginUrl("/tologin");


        //设置未授权页面
        bean.setUnauthorizedUrl("/noauth");

        return bean;
    }


    /*第二步*/
    //DefaultWebSecurityManager
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm   需要在方法中传入参数，@Qualifier("userRealm")
        // UserRealm userRealm,也可以在userRealm的@bean中添加（name=”userRealm“）
        securityManager.setRealm(userRealm);

        return securityManager;
    }

    /*第一步*/
    //创建realm对象,需要自定义类
    //把一个Bean放入@Configuration里，public Bean类型 别名（）{return new Bean类型}
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }
}
