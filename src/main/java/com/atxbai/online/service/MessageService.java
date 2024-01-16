package com.atxbai.online.service;

import com.atxbai.online.model.vo.MessageVo;
import com.atxbai.online.model.vo.teacher.KickOutStudentReqVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-14 17:10
 * @content:
 */
public interface MessageService {
    Page<MessageVo> getMessageListDescBySno(Page<MessageVo> page, String sno);

    void deliverMessage(String sno, Integer tno,Integer pid,String content);

    void saveMessageBySno(KickOutStudentReqVo kickOutStudentReqVo);
}
