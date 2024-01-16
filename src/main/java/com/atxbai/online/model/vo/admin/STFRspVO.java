package com.atxbai.online.model.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-15 14:40
 * @content:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("师生比返回体")
public class STFRspVO {

    @ApiModelProperty("学生数量")
    private Long studentNum;

    @ApiModelProperty("教师数量")
    private Long teacherNum;
}
