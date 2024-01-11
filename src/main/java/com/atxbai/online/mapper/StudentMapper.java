package com.atxbai.online.mapper;

import com.atxbai.online.model.pojo.Resume;
import com.atxbai.online.model.pojo.Student;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-09 15:02
 * @content:
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    /**
     * 这个方法用于效验权限
     * @return
     */
    default Student findByUsername(String username){
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        // 判断 username 和 sno 是否相同
        return selectOne(wrapper.eq(Student::getSno,username));
    }

    int add(Resume resume);

    int updateResume(Resume resume);
}
