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
@Order(9999)
public class SecurityConfig2 extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 关闭
//        httpSecurity.csrf().disable().formLogin();
        System.out.println("SecurityConfig2 order(9999) : %s" + httpSecurity);
        httpSecurity.authorizeRequests().antMatchers("/hello").permitAll();
    }

    public void configure(WebSecurity webSecurity) throws Exception {
//        webSecurity.securityInterceptor()
    }

    public void init(WebSecurity web) throws Exception {
        HttpSecurity http = web.getSharedObject(HttpSecurity.class);
        configure(http);
    }

}