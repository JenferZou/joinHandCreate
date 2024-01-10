package com.atxbai.online.exception;

/**
 * @author 小白
 * @version 1.0
 * create: 2024-01-09 23:18
 * content: 通用异常接口
 */
public interface BaseExceptionInterface {
    /**
     * 用于获取异常码
     * @return 返回异常码
     */
    String getErrorCode();

    /**
     * 用于获取异常信息
     * @return 返回异常信息
     */
    String getErrorMessage();

}
