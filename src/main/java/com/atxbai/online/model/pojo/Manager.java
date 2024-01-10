package com.atxbai.online.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@TableName("manager")
public class Manager {
    @TableId(value = "no")
    private String no;

    @TableField("name")
    private String name;

    @TableField("major")
    private String major;

    @TableField("phone")
    private String phone;

    @TableField("department")
    private String department;

    @TableField("password")
    private String password;

}
