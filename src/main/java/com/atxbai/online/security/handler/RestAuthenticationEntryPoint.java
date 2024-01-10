package com.atxbai.online.security.handler;


import com.atxbai.online.common.responseUtils.Response;
import com.atxbai.online.common.responseUtils.ResponseCodeEnum;
import com.atxbai.online.common.securityUtils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 小白
 * @version 1.0
 * @create: 2023-12-29 16:06
 * @content: 自定义我们认证异常处理对象
 * 处理用户未登录访问受保护的资源。AuthenticationEntryPoint是Spring Security框架中的一个接口，
 * 用于定义认证失败时如何处理。当用户尝试访问受保护的资源，但未提供正确的认证信息时，
 * Spring Security会调用 AuthenticationEntryPoint 接口的实现类来处理认证失败的情况。
 */
@Slf4j
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // AuthenticationEntryPoint接口只定义了一个方法commence，用于处理认证失败的情况。
    // 在该方法中，实现类可以根据需要执行不同的操作，例如重定向用户到登录页面、返回401错误页面等
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 提示
        log.warn("用户未登录访问受保护的资源: ", authException);
        // 用户认证不足
        if (authException instanceof InsufficientAuthenticationException) {
            ResultUtil.fail(response, HttpStatus.UNAUTHORIZED.value(), Response.fail(ResponseCodeEnum.UNAUTHORIZED));
        }
        // 这个是对应的是 TokenAuthenticationFilter 中，我们抛出的 Token 已失效 和 Token 不可用 的异常
        ResultUtil.fail(response, HttpStatus.UNAUTHORIZED.value(), Response.fail(authException.getMessage()));
    }
}
