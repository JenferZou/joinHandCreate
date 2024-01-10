package com.atxbai.online.config.security;


import com.atxbai.online.security.filter.JwtAuthenticationFilter;
import com.atxbai.online.security.handler.RestAuthenticationFailureHandler;
import com.atxbai.online.security.handler.RestAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author 小白
 * @version 1.0
 * @create: 2023-12-28 18:24
 * @content:
 */
@Configuration
@EnableWebSecurity
public class JwtAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    /**
     * 认证成功处理器
     */
    @Autowired
    private RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;
    /**
     * 认证失败处理器
     */
    @Autowired
    private RestAuthenticationFailureHandler restAuthenticationFailureHandler;

    /**
     * 使用 BCryptPasswordEncoder
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 使用我们创建的 UserDetailService
     */
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity builder){
        // 1. 自定义的用于 JWT 身份验证的过滤器
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
        // 将默认的认证过滤器通过 set 的方式添加到我们创建的对象中
        filter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));

        // 2. 设置登录认证对应的处理类（成功处理、失败处理）
        filter.setAuthenticationSuccessHandler(restAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(restAuthenticationFailureHandler);

        // 3. 直接使用 DaoAuthenticationProvider, 它是 Spring Security 提供的默认的身份验证提供者之一
        // 这个类要调用我们的 userDetailService 的实现类, 我们将我们写的设置进去
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // 设置 userDetailService，用于获取用户的详细信息
        provider.setUserDetailsService(userDetailsService);

        // 4.设置加密算法
        provider.setPasswordEncoder(passwordEncoder);
        // 将我们创建的 provider，添加到 httpSecurity 上
        builder.authenticationProvider(provider);

        // 5.将我们创建的拦截器添加到 UsernamePasswordAuthenticationFilter 之前执行
        builder.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

    }


}
