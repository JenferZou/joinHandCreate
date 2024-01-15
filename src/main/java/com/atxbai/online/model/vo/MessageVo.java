package com.atxbai.online.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageVo {

    private int mid;
    /**
     * 学生 ID
     */
    private String sno;

    /**
     * 学生姓名
     */
    private String sName;

    /**
     * 教师 ID
     */
    private int tno;

    /**
     * 指导老师
     */
    private String mentor;
    /**
     * 项目 ID
     */
    private String name;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 消息内容
     */
    private String content;
}
