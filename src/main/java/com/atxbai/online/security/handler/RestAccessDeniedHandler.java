package com.atxbai.online.security.handler;


import com.atxbai.online.common.responseUtils.Response;
import com.atxbai.online.common.responseUtils.ResponseCodeEnum;
import com.atxbai.online.common.securityUtils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author 小白
 * @version 1.0
 */
@Slf4j
@Component
public class RestAccessDeniedHandler  implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.warn("登录成功访问收保护的资源，但是权限不够: ", accessDeniedException.getCause());

        ResultUtil.fail(response, Response.fail(ResponseCodeEnum.FORBIDDEN));
    }
}
