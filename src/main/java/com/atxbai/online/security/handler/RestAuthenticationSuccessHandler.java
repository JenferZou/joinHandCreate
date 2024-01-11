package com.atxbai.online.security.handler;


import com.atxbai.online.common.responseUtils.Response;
import com.atxbai.online.common.securityUtils.JwtTokenHelper;
import com.atxbai.online.common.securityUtils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 小白
 * @version 1.0
 * @content: 认证成功处理器
 */
@Slf4j
@Component
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 我们创建的 JWT 的工具类
     */
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 从 authentication 对象中获取用户的 UserDetails 实例，这里是获取用户的用户名
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 通过用户名生成 Token
        String username = userDetails.getUsername();
        // 用我们 JWT 工具类，创建我们的 Token
        String token = jwtTokenHelper.generateToken(username);
        // 返回的 title
        String title = null;
        // 利用用户名 设置title
        if (username.startsWith("1000")){
            title = "学生";
        } else if (username.startsWith("2000")) {
            title = "教师";
        }else{
            title = "管理员";
        }

        // 返回 token
        com.atxbai.online.model.vo.login.LoginRspVO loginRspVO = com.atxbai.online.model.vo.login.LoginRspVO.builder().token(token).title(title).build();

        // TODO 通过工具类返回对象
        ResultUtil.ok(response, Response.success(loginRspVO));
    }
}
