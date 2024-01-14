package com.atxbai.online.mapper;

import com.atxbai.online.model.pojo.Manager;
import com.atxbai.online.model.pojo.Student;
import com.atxbai.online.model.pojo.Teacher;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-10 16:27
 * @content:
 */
@Mapper
public interface ManagerMapper extends BaseMapper<Manager> {

    default Manager findByUsername(String username){
        LambdaQueryWrapper<Manager> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Manager::getNo,Integer.valueOf(username));
        return selectOne(wrapper);
    }
}
