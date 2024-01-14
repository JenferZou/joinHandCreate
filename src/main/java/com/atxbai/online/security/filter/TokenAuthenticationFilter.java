package com.atxbai.online.security.filter;


import com.atxbai.online.common.securityUtils.JwtTokenHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


/**
 * @author 小白
 * @version 1.0
 * @content: 用于鉴权
 */
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    /**
     * JwtTokenHelper 生成 JWT 的工具类
     */
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    /**
     * 实现数据认证的具体流程接口
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * AuthenticationEntryPoint 用来解决匿名用户访问无权限资源时的异常
     */
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    /**
     * token 请求头的前缀
     */
    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

    /**
     * token 请求头中的 key 值
     */
    @Value("${jwt.tokenHeaderKey}")
    private String tokenHeaderKey;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        // 这个过滤器会都所有的请求生效，我们要判断只有 /admin 、/student 、/teacher才 判断，其他放行
        if (requestURI.startsWith("/admin") || requestURI.startsWith("/student") || requestURI.startsWith("/teacher")){
            // 从请求头中获取 key 为 Authorization 的值
            String header = request.getHeader(tokenHeaderKey);
            // 判断 value 值是否以 Bearer 开头
            if (StringUtils.startsWith(header, tokenPrefix)) {
                // 截取 Token 令牌
                String token = StringUtils.substring(header, tokenPrefix.length()+1);

                // 判断 Token
                // 当 token 不为空时，判断 token 是否有效
                if (StringUtils.isNotBlank(token)) {
                    try {
                        // 校验 Token 是否可用, 若解析异常，针对不同异常做出不同的响应参数
                        jwtTokenHelper.validateToken(token);
                    } catch (SignatureException | MalformedJwtException | UnsupportedJwtException |
                             IllegalArgumentException e) {
                        // 抛出异常，统一让 AuthenticationEntryPoint 处理响应参数
                        // 在 AuthenticationEntryPoint 接口中，只有一个 commence() 方法，用来开启认证方案。
                        authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token 不可用"));
                        return;
                    } catch (ExpiredJwtException e) {
                        authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token 已失效"));
                        return;
                    }
                }

                // 通过上的代码表示 token 是有效的，从 Token 中解析出用户名
                String username = jwtTokenHelper.getUsernameByToken(token);

                if (StringUtils.isNotBlank(username)
                        && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                    // 根据用户名获取用户详情信息 UserDetails 对象:表示用户的核心信息 (用户名, 用户密码, 权限等信息)
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    // 将用户信息存入 authentication，方便后续校验 ，UPAT 是它的实现类型
                    // 用户认证（Authentication）和 用户授权（Authorization）
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());
                    // 这个details属性用于存储额外的认证相关的细节信息，例如用户在进行认证时的IP地址、浏览器信息等。
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // 将 authentication 存入 ThreadLocal，方便后续获取用户信息,存放在线程中
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        // 继续执行写一个过滤器
        filterChain.doFilter(request, response);
    }
}
