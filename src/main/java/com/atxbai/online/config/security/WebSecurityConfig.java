package com.atxbai.online.config.security;


import com.atxbai.online.security.filter.TokenAuthenticationFilter;
import com.atxbai.online.security.handler.RestAccessDeniedHandler;
import com.atxbai.online.security.handler.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author 小白
 * @version 1.0
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    // 我们创建的用户认证相关配置
    private JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;

    @Autowired
    // 我们创建的用户未登录访问受保护的资源
    private RestAuthenticationEntryPoint authEntryPoint;
    @Autowired
    // 登录成功访问收保护的资源，但是权限不够
    private RestAccessDeniedHandler deniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable() // 禁用 csrf
                // 禁用表单登录
                .formLogin().disable()
                // 设置用户登录认证相关配置 new DefaultLoginPageConfigurer<>() 这个是使用默认页面
                .apply(jwtAuthenticationSecurityConfig)
            .and()
                // 配置需要认证的路径
                .authorizeHttpRequests()
                .mvcMatchers("/admin/**","/student/**","/teacher/**","/project/**","/delieverResume/**").authenticated()
                .anyRequest().permitAll()
            .and()
                // 前后端分离，无需创建会话
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                // 处理用户未登录访问受保护的资源的情况
                .httpBasic().authenticationEntryPoint(authEntryPoint)
            .and()
                // 处理登录成功后访问受保护的资源，但是权限不够的情况
                .exceptionHandling().accessDeniedHandler(deniedHandler)
            .and()
                // 将 Token 校验过滤器添加到用户认证过滤器之前
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Token 校验过滤器
     * @return
     */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }
}
