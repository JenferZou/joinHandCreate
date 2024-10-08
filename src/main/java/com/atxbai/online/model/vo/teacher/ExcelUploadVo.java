package com.atxbai.online.model.vo.teacher;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> Create <br> 2024-01-15 21:11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelUploadVo {
    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("专业")
    private String major;

    @ExcelProperty("电话")
    private String phone;

    @ExcelProperty("所属部门")
    private String department;

}
