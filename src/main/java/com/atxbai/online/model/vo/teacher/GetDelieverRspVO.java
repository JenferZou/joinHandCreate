package com.atxbai.online.model.vo.teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-11 16:43
 * @content: 返回展示
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetDelieverRspVO {
    private int pid;
    private String projectName;
    private String sName;
    private String sno;
    private String smajor;
    private String content;
}
