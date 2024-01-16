package com.atxbai.online.controller;

import com.atxbai.online.common.responseUtils.Response;
import com.atxbai.online.common.securityUtils.JwtTokenHelper;
import com.atxbai.online.model.pojo.Resume;
import com.atxbai.online.model.pojo.Student;
import com.atxbai.online.model.vo.EditPasswordVo;
import com.atxbai.online.service.ResumeService;
import com.atxbai.online.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@PreAuthorize("hasRole('ROLE_STUDENT')")
public class StudentController {

    @Resource
    private StudentService studentService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Resource
    private ResumeService resumeService;

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

    @GetMapping("/selectById")
    @ApiOperation(value = "个人简历页面个人信息")
    public Response selectById(@RequestHeader("Authorization") String userToken){
        String token = StringUtils.substring(userToken, 7);
        String sno = jwtTokenHelper.getUsernameByToken(token);
        Student student = studentService.selectBySno(sno);
        return Response.success(student);
    }

    @GetMapping("/StudentResume")
    @ApiOperation(value = "展示个人简历页面个人经历")
    public Response StudentResume(@RequestHeader("Authorization") String userToken){
        String token = StringUtils.substring(userToken, 7);
        String sno = jwtTokenHelper.getUsernameByToken(token);
        Resume resume = resumeService.selectBySno(sno);
        return Response.success(resume);
    }



    @PutMapping("/editMessage")
    @ApiOperation(value = "修改个人信息")
    public Response editMessage(@RequestBody Student student){
        studentService.updateStudent(student);
        return Response.success();
    }


    @PostMapping("/edit")
    @ApiOperation(value = "修改个人经历部分")
    public Response edit(@RequestBody Resume resume){
        resumeService.updateResume(resume);
        return Response.success();
    }


    @PostMapping("/editpass")
    @ApiOperation(value = "修改密码")
    public Response editpass(@RequestHeader("Authorization") String userToken,@RequestBody EditPasswordVo editPasswordVo ){
       return studentService.editpass(userToken,editPasswordVo);
    }


}
