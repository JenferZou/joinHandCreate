package com.atxbai.online.mapper;

import com.atxbai.online.model.pojo.Student;
import com.atxbai.online.model.pojo.Teacher;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-10 16:25
 * @content:
 */
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {
    default Teacher findByUsername(String username){
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getNo,username);
        return selectOne(wrapper);
    }

    /**
     * 修改老师信息
     * @param teacher 老师实体
     * @return
     */
    boolean updateTeacher(Teacher teacher);
}
