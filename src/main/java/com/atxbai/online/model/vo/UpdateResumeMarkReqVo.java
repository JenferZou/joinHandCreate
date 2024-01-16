package com.atxbai.online.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> Create <br> 2024-01-16 15:11
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("更新简历的ReqVo")
public class UpdateResumeMarkReqVo {
    private Integer id;
    private Integer mark;
}
