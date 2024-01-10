package com.atxbai.online.model.VO.student;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-09 17:54
 * @content:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "时间类测试 VO")
public class LocalDateTimeReqVO {

    @NotBlank(message = "名称不能为空")
    private String name;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6,max = 12,message = "密码长度要在 6 - 12 位")
    private String password;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDate updateDate;

}
