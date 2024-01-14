package com.atxbai.online.model.vo.teacher;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-14 15:29
 * @content:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "请求学生简历和个人信息 VO")
public class LookStudentResumeReqVO {

    @NotBlank
    @ApiModelProperty(value = "学生学号")
    private String sno;
}
