package com.atxbai.online.model.vo.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-15 14:40
 * @content:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class STFRspVO {

    private Long studentNum;

    private Long teacherNum;
}
