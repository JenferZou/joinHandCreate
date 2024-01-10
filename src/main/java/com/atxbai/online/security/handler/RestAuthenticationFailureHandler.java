package com.atxbai.online.security.handler;

import com.atxbai.weblog.common.enums.ResponseCodeEnum;
import com.atxbai.weblog.common.utils.Response;
import com.atxbai.weblog.jwt.exception.UsernameOrPasswordNullException;
import com.atxbai.weblog.jwt.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 小白
 * @version 1.0
 * create: 2023-12-28 18:13
 * content: 自定义认证失败处理器，当失败时 IOC 容器调用
 */
@Slf4j
@Component
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 提示错误
        log.warn("AuthenticationException: ", exception.getMessage());
        // 判断异常
        if(exception instanceof UsernameOrPasswordNullException){
            // 用户名或密码为空
            ResultUtil.fail(response, Response.fail(exception.getMessage()));

        } else if (exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException){
            // 这个异常是 spring security 自带的认证不通过返回的异常
            // 用户名或密码错误
            ResultUtil.fail(response, Response.fail(ResponseCodeEnum.USERNAME_OR_PWD_ERROR));
        }

        // 登录失败
        ResultUtil.fail(response, Response.fail(ResponseCodeEnum.LOGIN_FAIL));
    }
}
