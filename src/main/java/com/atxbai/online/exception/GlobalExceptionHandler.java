package com.atxbai.online.exception;


import com.atxbai.online.common.responseUtils.Response;
import com.atxbai.online.common.responseUtils.ResponseCodeEnum;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author 小白
 * @version 1.0
 * create: 2024-01-09 23:18
 * content: 全局异常处理类
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕获自定义业务异常
     */
    @ResponseBody
    @ExceptionHandler({BizException.class})
    public Response<Object> handleBizException(HttpServletRequest request, BizException e){
        log.warn("{} request fail,errorCode: {}, errorMessage: {}",request.getRequestURI(),e.getErrorCode(),e.getErrorMessage());
        return Response.fail(e);
    }

    /**
     * 其他类型异常
     * @param request
     * @param e
     */
    @ResponseBody
    @ExceptionHandler({ Exception.class })
    public Response<Object> handleOtherException(HttpServletRequest request, Exception e) {
        log.error("{} request error, ", request.getRequestURI(), e);
        return Response.fail(ResponseCodeEnum.SYSTEM_ERROR);
    }

    /**
     * 这个异常专门处理实体类效验，如何没用异常不会走这里，
     * 如果有异常，通过抛出异常后，我们将进行分析异常。
     * @param request 请求体
     * @param e 参数异常
     * @return 返回错误信息
     */
    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Response<Object> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e){
        // 参数错误异常码
        String errorCode = ResponseCodeEnum.PARAM_NOT_VALID.getErrorCode();

        // 获取 BindingResult
        BindingResult bindingResult = e.getBindingResult();

        // 创建一个可以改变的 StringBuilder 对象
        StringBuilder sb = new StringBuilder();

        // 获取校验不通过的字段，并组合错误信息，格式为： email 邮箱格式不正确, 当前值: '123124qq.com'
        Optional.ofNullable(bindingResult.getFieldErrors()).ifPresent(fieldErrors -> {
            fieldErrors.forEach(error -> {
                sb.append(error.getField())
                        .append(" ")
                        .append(error.getDefaultMessage())
                        .append(", 当前值：'")
                        .append(error.getRejectedValue())
                        .append("'; ");
            });
        });

        // 错误信息
        String errorMessage = sb.toString();

        log.warn("{} request error, errorCode: {}, errorMessage: {}", request.getRequestURI(), errorCode, errorMessage);

        return Response.fail(errorCode,errorMessage);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public void throwAccessDeniedException(AccessDeniedException e) throws AccessDeniedException{
        // 捕获到鉴权失败异常，主动抛出，交给 RestAccessDeniedHandler 去处理
        log.info("====== 捕获到 AccessDeniedException 异常 ==============");
        // 抛出异常
        throw e;
    }

}
