package com.atxbai.online.model.vo.teacher;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-12 10:02
 * @content: 根据名称分页查询
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "根据名称模糊查询分页信息")
public class SearchNameRDReqVO {
    @NotBlank(message = "分页不为空")
    @ApiModelProperty("当前的页码")
    private String page;

    @NotBlank(message = "限制的分页的数量")
    @ApiModelProperty("当前的分页")
    private String limit;

    @NotNull(message = "搜索的内容不能为空")
    @ApiModelProperty("学生姓名")
    private String searchName;
}
