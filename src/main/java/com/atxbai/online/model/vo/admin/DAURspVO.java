package com.atxbai.online.model.vo.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-15 11:08
 * @content:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("日活响应体")
public class DAURspVO {
    /**
     * 日期集合
     */
    @ApiModelProperty("时间集合")
    private List<LocalDate> localDates = new ArrayList<>();
    /**
     * 人数集合
     */
    @ApiModelProperty("每日人数")
    private List<Long> nums = new ArrayList<>();
}
