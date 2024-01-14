package com.atxbai.online.mapper;

import com.atxbai.online.model.pojo.Resume;
import com.atxbai.online.model.pojo.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import javax.annotation.Resource;

/**
 * @author Mr.LL
 * @version 1.0
 */
@Mapper
public interface ResumeMapper extends BaseMapper<Resume> {


    @Select("SELECT * from resume where sno = #{sno}")
    Resume selectBySno(String sno);
}