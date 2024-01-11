package com.atxbai.online.controller;

import com.atxbai.online.common.responseUtils.Response;
import com.atxbai.online.model.VO.TestSaveReqVO;
import com.atxbai.online.model.pojo.Resume;
import com.atxbai.online.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-09 15:06
 * @content: 学生控制类
 */
@Slf4j
@RestController
@RequestMapping("/student")
@Api(tags = "学生模块")
//@PreAuthorize("hasRole('ROLE_STUDENT')")
public class StudentController {

    @Resource
    private StudentService studentService;

    @PostMapping("/test/save")
    @ApiOperation(value = "测试接口")
    public Response test(@RequestBody @Validated TestSaveReqVO testSaveReqVO){
        return Response.success(testSaveReqVO);
    }

    @PostMapping("/add")
    @ApiOperation(value = "增加简历")
    public Response add(@RequestBody @Validated Resume resume){
        studentService.add(resume);
        return Response.success();
    }


    @PutMapping("/update")
    @ApiOperation(value = "修改简历")
    public Response updateResume(@RequestBody @Validated Resume resume){
        studentService.updateResume(resume);
        return Response.success();
    }
}
