package com.atxbai.online.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p> Create <br> 2024-01-12 16:59
 **/

@Data
public class ProjectPageReqVo implements Serializable {
    //项目名
    @ApiModelProperty("项目名")
    private String name;

    //项目开始时间
    @ApiModelProperty("项目开始时间")
    private LocalDateTime startTime;

    //项目所需专业
    @ApiModelProperty("项目所需专业")
    private String needMajor;

    //项目对标比赛
    @ApiModelProperty("项目对标比赛")
    private String expectedCompetition;


    //页码
    @ApiModelProperty("页码")
    private int page;

    //每页显示记录数
    @ApiModelProperty("每页显示记录数")
    private int pageSize;



}
