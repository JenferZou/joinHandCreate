package com.atxbai.online.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-14 17:07
 * @content: 消息实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("message")
public class Message {
    @TableId(type = IdType.AUTO)
    private int mid;
    /**
     * 学生 ID
     */
    private String sno;
    /**
     * 教师 ID
     */
    private int tno;
    /**
     * 项目 ID
     */
    private int pid;
    /**
     * 创建时间
     */
    private LocalDateTime createDateTime;
    /**
     * 消息内容
     */
    private String content;
}
