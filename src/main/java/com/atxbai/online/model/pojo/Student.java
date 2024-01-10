package com.atxbai.online.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@TableName("student")
@Data
@AllArgsConstructor
public class Student {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("sno")
    private String sno;

    @TableField("resumeId")
    private String resumeId;

    @TableField("sName")
    private String sName;

    @TableField("gender")
    private String gender;

    @TableField("sDepartment")
    private String sDepartment;

    @TableField("sMajor")
    private String sMajor;

    @TableField("className")
    private String className;

    @TableField("sPhone")
    private String sPhone;

    @TableField("password")
    private String password;

}
