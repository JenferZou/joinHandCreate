package com.atxbai.online.model.vo.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 小白
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRspVO {
    /**
     * token 值
     */
    private String token;

    /**
     * 判断是否是管理员
     */
    private String title;
}
