package com.atxbai.online.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-15 11:19
 * @content:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("login")
public class Login {
    @TableId(type = IdType.AUTO)
    private int id;

    @TableField("loginId")
    private String loginId;

    @TableField("loginName")
    private String loginName;

    @TableField("loginTime")
    private LocalDate loginTime;

    public Login(String loginId, String loginName, LocalDate loginTime) {
        this.loginId = loginId;
        this.loginName = loginName;
        this.loginTime = loginTime;
    }
}
