package com.atxbai.online.model.vo.admin;

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
public class DAURspVO {
    /**
     * 日期集合
     */
    private List<LocalDate> localDates = new ArrayList<>();
    /**
     * 人数集合
     */
    private List<Long> nums = new ArrayList<>();
}
