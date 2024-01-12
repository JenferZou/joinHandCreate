package com.atxbai.online.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDataVO {
    private String keyword;
    private String className;
    private String sMajor;
    private String sDepartment;
    private Integer page;
    private Integer limit;
}
