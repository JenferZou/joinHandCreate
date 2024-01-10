package com.atxbai.online.security.filter;

import com.atxbai.weblog.jwt.exception.UsernameOrPasswordNullException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author 小白
 * @version 1.0
 * @create: 2023-12-28 14:29
 * @content: 这个过滤器是用于认证，简单来说，就是登录认证
 * 重新 spring security 第一层的拦截器
 * 在这个阶段，只有 username 和 password
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 在构造器中，指定用户登录的访问地址，重新父类的方法
     */
    public JwtAuthenticationFilter(){
        // 通过 AntPathRequestMatcher 指定了处理用户登录的访问地址
        // 当请求路径匹配 /login 并且请求方法为 POST 时，该过滤器将被触发
        super(new AntPathRequestMatcher("/login","POST"));
    }

    @Override
    // 用于实现用户身份验证的具体逻辑，该方法可能包含身份验证的逻辑
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException{
        // 创建 JackJson 的功能对象
        ObjectMapper mapper = new ObjectMapper();

        // 解析提交的 JSON 对象,不知道传过来的是什么 Json 对象，可以转为 JsonNode 对象
        JsonNode jsonNode = mapper.readTree(request.getInputStream());
        // 通过jsonNode 获取用户名和密码
        JsonNode usernameNode = jsonNode.get("username");
        JsonNode passwordNode = jsonNode.get("password");

        // 判断用户、密码是否为空
        if (Objects.isNull(usernameNode) || Objects.isNull(passwordNode)
                || StringUtils.isBlank(usernameNode.textValue()) || StringUtils.isBlank(passwordNode.textValue())){
            // 如果为空,返回用户名密码为空认证异常 AuthenticationException
            // 该异常会被 异常认证处理器捕获，我们自定义的异常，抛出异常后，去认证失败处理器
            throw new UsernameOrPasswordNullException("用户名或密码不能为空");
        }

        // 读取数据
        // region 区别 textValue 和 asText

        // textValue 只针对 String 类型，如果不为 String 为null
        // asText 如果不是 String 类型，强转为 String
        // endregion
        String username = usernameNode.textValue();
        String password = passwordNode.textValue();

        // 将用户名、密码封装到 Authentication 中
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(username, password);

        // 通过 authenticate 方法进行认证 Authentication, 返回的是一个 Authentication
        // 成功认证用户和密码后，---> UserDetailService --> 认证成功处理器
        return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }
}
