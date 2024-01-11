package com.atxbai.online.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@TableName("delieverresume")
public class DelieverResume {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField("pid")
    private Integer pid;


    @TableField("sno")
    private String sno;

    @TableField("sName")
    private String sName;

    @TableField("tno")
    private Integer tno;


    @TableField("mentor")
    private String mentor;


    @TableField("projectName")
    private String projectName;


    @TableField("sMajor")
    private String sMajor;


    @TableField("content")
    private String content;

    @TableField("mark")
    private Integer mark;

}
