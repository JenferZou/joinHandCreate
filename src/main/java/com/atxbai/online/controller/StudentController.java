package com.atxbai.online.controller;

import com.atxbai.online.model.DO.StudentDO;
import com.atxbai.online.common.responseUtils.Response;
import com.atxbai.online.model.VO.student.LocalDateTimeReqVO;
import com.atxbai.online.model.VO.student.StudentSaveReqVO;
import com.atxbai.online.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-09 15:06
 * @content: 学生控制类
 */
@RestController
@RequestMapping("/student")
@Api(tags = "学生模块")
@Slf4j
public class StudentController {

    @Autowired
    private StudentService service;

    @GetMapping("/test/all")
    @ApiOperation(value = "测试接口-查询全部学生信息")
    public Response test(){
        // 查询数据库
        //List<StudentDO> list = service.list();
        ArrayList list = new ArrayList();
        return Response.success(list);
    }

    @GetMapping("/test/{id}")
    @ApiOperation(value = "测试接口-根据ID查询学生对象")
    public Response testById(@PathVariable("id") Long id){
        // 查询数据库
        //StudentDO studentDO = service.getById(id);
        StudentDO studentDO = StudentDO.builder().id(id).build();
        return Response.success(studentDO);
    }

    @PostMapping("/test/save")
    @ApiOperation(value = "测试接口-保存学生对象")
    public Response testSave(@RequestBody @Validated StudentSaveReqVO studentSaveReqVO){
        log.warn("测试:{}",studentSaveReqVO);
        return Response.success(studentSaveReqVO);
    }

    @PostMapping("/test/date")
    @ApiOperation(value = "测试接口-日期类")
    public Response testLocalDateTime(@RequestBody @Validated LocalDateTimeReqVO localDateTimeReqVO){
        log.warn("测试:{}",localDateTimeReqVO);
        return Response.success(localDateTimeReqVO);
    }
}
