package com.atxbai.online.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@TableName("resume")
@Data
@AllArgsConstructor
public class Resume {

    @TableId(value = "resumeId",type = IdType.AUTO)
    private Integer resumeId;

    @TableField("sno")
    private String sno;

    @TableField("personalAdvantage")
    private String personalAdvantage;

    @TableField("internshipExperience")
    private String internshipExperience;

    @TableField("awardExperience")
    private String awardExperience;

    @TableField("projectExperience")
    private String projectExperience;

    @TableField("certificate")
    private String certificate;

}
