package com.atxbai.online.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentVO {
    private Integer id;
    private String sno;
    private String resumeId;
    private String sName;
    private String gender;
    private String sDepartment;
    private String sMajor;
    private String className;
    private String sPhone;
    private String password;
}
