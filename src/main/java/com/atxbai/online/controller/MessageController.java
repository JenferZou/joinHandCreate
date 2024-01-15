package com.atxbai.online.controller;

import com.atxbai.online.common.responseUtils.PageResponse;
import com.atxbai.online.common.responseUtils.Response;
import com.atxbai.online.common.responseUtils.ResponseCodeEnum;
import com.atxbai.online.common.securityUtils.JwtTokenHelper;
import com.atxbai.online.exception.BizException;
import com.atxbai.online.model.pojo.DelieverResume;
import com.atxbai.online.model.pojo.Message;
import com.atxbai.online.model.pojo.Student;
import com.atxbai.online.model.vo.MessageVo;
import com.atxbai.online.service.MessageService;
import com.atxbai.online.service.StudentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
@Api(tags = "消息通知模块")
public class MessageController {

    @Autowired
    private MessageService messageService;



    @Autowired
    private JwtTokenHelper jwtTokenHelper;


    @Autowired
    private StudentService studentService;


    @GetMapping("/getMessageBySno")
    public PageResponse<MessageVo> getMessageBySno(@RequestHeader("Authorization") String userToken, @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize){
        String token = StringUtils.substring(userToken, 7);
        String sno = jwtTokenHelper.getUsernameByToken(token);
        Student student = studentService.selectBySno(sno);
        if(student==null){
            throw new BizException(ResponseCodeEnum.CODE_600);
        }
        Page<MessageVo> messagePage = new Page<>(pageNo == null ? 1 : pageNo, pageSize == null ? 5 : pageSize);
        Page<MessageVo> page = messageService.getMessageListDescBySno(messagePage, sno);
        return PageResponse.success(page, page.getRecords());

    }



}
