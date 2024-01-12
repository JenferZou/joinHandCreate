package com.atxbai.online.model.vo.teacher;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-12 14:00
 * @content:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "同意审批学生请求")
public class AgreeDelieverReqVO {
    @NotNull(message = "不能为空")
    private int pid;
    @NotNull(message = "不能为空")
    private String sno;
}
