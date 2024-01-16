package com.atxbai.online.mapper;

import com.atxbai.online.model.pojo.DelieverResume;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface DelieverResumeMapper extends BaseMapper<DelieverResume> {
    /**
     * 设置状态
     * @param id 项目id
     * @param i 状态
     * @return
     */

    int setMark(@Param("id") Integer id,@Param("i") int i);

    /**
     * 获取学生id
     * @param id
     * @return
     */

    Set<String> selectSno(Integer id);
}
