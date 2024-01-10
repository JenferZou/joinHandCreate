package com.atxbai.online.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author 小白
 * @version 1.0
 * create: 2023-12-28 15:46
 * content: 这个异常是继承 AuthenticationException
 *          用于细分认证异常，只有继承该类，才能被认证失败处理器捕获
 */
public class UsernameOrPasswordNullException extends AuthenticationException {

    public UsernameOrPasswordNullException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UsernameOrPasswordNullException(String msg) {
        super(msg);
    }

}
