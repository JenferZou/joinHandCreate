package com.atxbai.online.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 小白
 * @version 1.0
 * create: 2024-01-09 23:18
 * content: 业务异常类
 */
@Getter
@Setter
public class BizException extends RuntimeException {

    //异常码
    private String errorCode;

    //错误信息
    private String errorMessage;

    /**
     * 业务异常类
     * @param baseExceptionInterface 传入 ResponseCodeEnum 的实现对象
     */
    public BizException(BaseExceptionInterface baseExceptionInterface){
        this.errorCode = baseExceptionInterface.getErrorCode();
        this.errorMessage = baseExceptionInterface.getErrorMessage();
    }
}
