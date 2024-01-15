package com.atxbai.online.model.vo.teacher;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-15 17:29
 * @content:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "修改保存教师信息")
public class EditMessageRspVO {
    @NotNull
    private Integer no;

    @NotBlank
    private String name;
    @NotBlank
    private String major;
    @NotBlank
    private String phone;
    @NotBlank
    private String department;
}
