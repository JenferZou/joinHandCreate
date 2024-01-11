package com.atxbai.online.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("resume")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resume {

    @TableId(type = IdType.AUTO)
    private Integer resumeId;

    /**
     * 学号
     */
    @TableField("sno")
    private String sno;

    /**
     * 个人优势
     */
    @TableField("personalAdvantage")
    private String personalAdvantage;

    /**
     * 实践经历
     */
    @TableField("internshipExperience")
    private String internshipExperience;

    /**
     * 获奖经历
     */
    @TableField("awardExperience")
    private String awardExperience;

    /**
     * 项目经历
     */
    @TableField("projectExperience")
    private String projectExperience;

    /**
     * 资格证书
     */
    @TableField("certificate")
    private String certificate;

}
