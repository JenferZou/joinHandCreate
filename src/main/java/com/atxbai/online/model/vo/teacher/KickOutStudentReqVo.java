package com.atxbai.online.model.vo.teacher;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> Create <br> 2024-01-16 09:32
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "踢出学生的Vo")
public class KickOutStudentReqVo {
    private String sno;
    private Integer pid;
    private Integer tno;
    private String projectName;
    @ApiModelProperty("指导老师")
    private String mentor;
}
