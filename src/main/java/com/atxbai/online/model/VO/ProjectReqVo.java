package com.atxbai.online.model.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectReqVo {
    @NotBlank(message = "指导老师不能为空不能为空")
    private String mentor;

    @NotBlank(message = "项目名称不能为空")
    private String name;

    //    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @NotBlank(message = "开始时间不能为空")
    private LocalDateTime startTime;

    @NotBlank(message = "专业要求不能为空")
    private String needMajor;

    @NotBlank(message = "对标竞赛不能为空")
    private String expectedCompetition;

    @NotBlank(message = "项目描述不能为空")
    private String content;


}
