package com.atxbai.online.common.responseUtils;


import com.atxbai.online.exception.BaseExceptionInterface;
import com.atxbai.online.exception.BizException;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 小白
 * @version 1.0
 * create: 2024-01-09 23:18
 * content: 响应工具类 : 用于封装返回数据
 */
@Data
public class Response<T> implements Serializable {

    // 是否成功,默认成功
    private boolean success = true;

    // 响应信息
    private String message;

    // 异常码
    private String errorCode;

    // 响应数据
    private T data;

    /**
     * == 成功响应1 默认返回 ==
     */
    public static <T> Response<T> success() {
        Response<T> response = new Response<>();
        response.setErrorCode("200");
        return response;
    }

    /**
     * == 成功响应2 包含数据 ==
     */
    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setErrorCode("200");
        response.setData(data);
        return response;
    }

    /**
     * == 失败响应1 默认返回 ==
     */
    public static <T> Response<T> fail() {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        return response;
    }

    /**
     * == 失败响应2 包含错误信息 ==
     */
    public static <T> Response<T> fail(String errorMessage) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setMessage(errorMessage);
        return response;
    }

    /**
     * == 失败响应3 包含错误码和错误信息 ==
     */
    public static <T> Response<T> fail(String errorCode, String errorMessage) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setErrorCode(errorCode);
        response.setMessage(errorMessage);
        return response;
    }

    /**
     * == 失败响应4 包含基础异常 ==
     */
    public static <T> Response<T> fail(BaseExceptionInterface baseExceptionInterface) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setErrorCode(baseExceptionInterface.getErrorCode());
        response.setMessage(baseExceptionInterface.getErrorMessage());
        return response;
    }

    /**
     * 自定义业务异常
     */
    public static <T> Response fail(BizException bizException) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setErrorCode(bizException.getErrorCode());
        response.setMessage(bizException.getErrorMessage());
        return response;
    }
}
