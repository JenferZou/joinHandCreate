package com.atxbai.online.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public class ProjectVo {

    private Integer id;

    private String tno;

    private String mentor;

    private String name;

    private Date startTime;


    private String needMajor;


    private String expectedCompetition;


}
