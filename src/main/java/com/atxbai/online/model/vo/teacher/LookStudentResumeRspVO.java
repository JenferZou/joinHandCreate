package com.atxbai.online.model.vo.teacher;

import com.atxbai.online.model.pojo.Resume;
import com.atxbai.online.model.pojo.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-14 15:29
 * @content:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LookStudentResumeRspVO {
    /**
     * 返回学生信息
     */
    private Student studentInfo;

    /**
     * 返回学生个人信息
     */
    private Resume resumeInfo;

}
