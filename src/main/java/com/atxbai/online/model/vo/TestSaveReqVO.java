package com.atxbai.online.model.VO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-09 17:24
 * @content: 请求保存 VO 对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "测试请求保存 VO")
public class TestSaveReqVO {
    @NotBlank(message = "名称不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6,max = 12,message = "密码长度要在 6 - 12 位")
    private String password;
}
