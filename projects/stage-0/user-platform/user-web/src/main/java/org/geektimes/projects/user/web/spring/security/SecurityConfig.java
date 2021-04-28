package org.geektimes.projects.user.web.spring.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security 配置类
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since TODO
 * Date : 2021-04-24
 */
@Configuration
@Order(10000)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 开启
//        httpSecurity.csrf();

        System.out.println(String.format("SecurityConfig order(10000) : %s",httpSecurity));
        httpSecurity.authorizeRequests().antMatchers("/hello").authenticated();
    }

    public void configure(WebSecurity webSecurity) throws Exception {
//        webSecurity.securityInterceptor()
    }

    @Override
    public void init(WebSecurity web) throws Exception {
        HttpSecurity http = web.getSharedObject(HttpSecurity.class);
        if(http == null){
            http = getHttp();
            //共享变量
            web.setSharedObject(HttpSecurity.class,http);
        }
        web.addSecurityFilterChainBuilder(http).postBuildAction(() -> {
            FilterSecurityInterceptor securityInterceptor = http
                    .getSharedObject(FilterSecurityInterceptor.class);
            web.securityInterceptor(securityInterceptor);
        });
    }
}
