package com.atxbai.online.model.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("teacher")
public class Teacher {

    @TableId("no")
    private Integer no;

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
