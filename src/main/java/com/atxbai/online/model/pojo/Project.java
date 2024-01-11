package com.atxbai.online.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@TableName("project")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField("tno")
    private Integer tno;

    @TableField("mentor")
    private String mentor;

    @TableField("name")
    private String name;

    @TableField("startTime")
    private LocalDateTime startTime;

    @TableField("content")
    private String content;

    @TableField("needMajor")
    private String needMajor;


    @TableField("expectedCompetition")
    private String expectedCompetition;




}
