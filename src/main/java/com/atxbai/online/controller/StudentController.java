package com.atxbai.online.controller;

import com.atxbai.online.common.responseUtils.Response;
import com.atxbai.online.common.securityUtils.JwtTokenHelper;
import com.atxbai.online.model.pojo.Resume;
import com.atxbai.online.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

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
