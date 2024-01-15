package com.atxbai.online.mapper;

import com.atxbai.online.model.pojo.Message;
import com.atxbai.online.model.vo.MessageVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-14 17:10
 * @content:
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    public Page<MessageVo> selectMessageListBySno(Page<MessageVo> page, String sno);
}
