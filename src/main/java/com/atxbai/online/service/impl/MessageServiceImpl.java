package com.atxbai.online.service.impl;

import com.atxbai.online.mapper.MessageMapper;
import com.atxbai.online.model.pojo.Message;
import com.atxbai.online.model.vo.MessageVo;
import com.atxbai.online.service.MessageService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-14 17:11
 * @content:
 */
@Service
@Slf4j
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    @Override
    public Page<MessageVo> getMessageListDescBySno(Page<MessageVo> page, String sno) {
        Page<MessageVo> messagePageVo = this.baseMapper.selectMessageListBySno(page, sno);
        return messagePageVo;

    }

    @Override
    public void deliverMessage(String sno, Integer tno,Integer pid,String content) {
        Message addMessage = new Message();
        addMessage.setSno(sno);
        addMessage.setTno(tno);
        addMessage.setContent(content);
        addMessage.setCreateDateTime(LocalDateTime.now());
        addMessage.setPid(pid);
        this.save(addMessage);
    }
}
