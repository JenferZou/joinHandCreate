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
 * @create: 2024-01-11 17:04
 * @content:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "申请审批分类对象")
public class GetDelieverReqVO {

    @NotBlank(message = "分页不为空")
    @ApiModelProperty("当前的页码")
    private String page;

    @NotBlank(message = "限制的分页的数量")
    @ApiModelProperty("当前的分页")
    private String limit;
}
