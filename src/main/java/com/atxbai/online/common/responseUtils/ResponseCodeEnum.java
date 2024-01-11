package com.atxbai.online.common.responseUtils;

import com.atxbai.online.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 小白
 * @version 1.0
 * create: 2024-01-09
 * content: 响应异常码
 */
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseExceptionInterface {
    // 通用异常状态码
    SYSTEM_ERROR("10000","出错啦，后台小哥正在努力修复中"),
    // 参数错误状态码
    PARAM_NOT_VALID("10001", "参数错误"),
    LOGIN_FAIL("10002", "登录失败"),
    USERNAME_OR_PWD_ERROR("10002", "用户名或密码错误"),
    UNAUTHORIZED("20001", "无访问权限，请先登录！"),
    FORBIDDEN("20002", "权限不足！"),
    CODE_600("600","请求参数错误"),
    ;

    // 异常码
    private String errorCode;
    // 错误信息
    private String errorMessage;

}
