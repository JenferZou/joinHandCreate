package com.atxbai.online.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "DelieverResumeVO")
public class DelieverResumeVo {
    @NotBlank(message = "id不能为空")
    private Integer id;

    @NotBlank(message = "pid不能为空")
    private Integer pid;

    @NotBlank(message = "学号不能为空")
    private String sno;

    @NotBlank(message = "姓名不能为空")
    private String sName;

    @NotBlank(message = "教师工号不能为空")
    private Integer tno;

    @NotBlank(message = "指导老师不能为空")
    private String mentor;

    @NotBlank(message = "项目名称不能为空")
    private String projectName;


    private String sMajor;

    private String content;

    @NotBlank(message = "mark不能为空")
    private Integer mark;
}
