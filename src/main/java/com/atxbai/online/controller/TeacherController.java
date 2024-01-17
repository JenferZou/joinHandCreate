package com.atxbai.online.controller;

import com.atxbai.online.common.responseUtils.PageResponse;
import com.atxbai.online.common.responseUtils.Response;


import com.atxbai.online.model.vo.EditPasswordVo;
import com.atxbai.online.model.vo.teacher.*;
import com.atxbai.online.service.ProjectService;
import com.atxbai.online.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-10 17:17
 * @content:
 */
@Slf4j
@RestController
@RequestMapping("/teacher")
@Api(tags = "教师模块")
@PreAuthorize("hasRole('ROLE_TEACHER')")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ProjectService projectService;



    @PostMapping("/getDeliever")
    @ApiOperation(value = "请求学生项目请求")
    public PageResponse getDeliever(@RequestBody @Validated GetDelieverReqVO getDelieverReqVO, @RequestHeader("Authorization") String header){
        return teacherService.getDeliever(getDelieverReqVO,header);
    }

    @PostMapping("/searchDeliever")
    @ApiOperation(value = "根据学生名称学生项目申请")
    public PageResponse searchRefuseDeliever(@RequestBody @Validated SearchNameRDReqVO searchNameRDReqVO, @RequestHeader("Authorization") String header){
        return teacherService.searchRefuseDeliever(searchNameRDReqVO,header);
    }

    @PostMapping("/refuseDeliever")
    @ApiOperation(value = "拒绝学生申请")
    public Response refuseDeliever(@RequestBody @Validated RefuseDelieverReqVO refuseDelieverReqVO,@RequestHeader("Authorization") String header){
        return teacherService.refuseDeliever(refuseDelieverReqVO,header);
    }

    @PostMapping("/agreeDeliever")
    @ApiOperation(value = "同意学生申请")
    public Response agreeDeliever(@RequestBody @Validated AgreeDelieverReqVO agreeDelieverReqVO, @RequestHeader("Authorization") String header){
        return teacherService.agreeDeliever(agreeDelieverReqVO,header);
    }

    @PostMapping("/lookStudentResume")
    @ApiOperation(value = "获取学生个人简历和项目信息")
    public Response lookStudentResume(@RequestBody @Validated LookStudentResumeReqVO lookStudentResumeReqVO){
        return teacherService.lookStudentResume(lookStudentResumeReqVO);
    }



    @GetMapping("/selectByInfo")
    @ApiOperation(value = "获取教师个人信息")
    public Response selectByInfo(@RequestHeader("Authorization") String header){
        return teacherService.selectByInfo(header);
    }

    @PostMapping("/editMessage")
    @ApiOperation(value = "保存修改信息")
    public Response editMessage(@RequestBody @Validated EditMessageRspVO editMessageRspVO){
        return teacherService.editMessage(editMessageRspVO);
    }

    @PostMapping("/updatePassword")
    @ApiOperation("修改教师的密码")
    public Response updatePassword(@RequestHeader("Authorization") String header,@RequestBody EditPasswordVo editPasswordVo ){
        return teacherService.updatePassword(header,editPasswordVo);
    }

    @PostMapping("/deleteProject")
    @ApiOperation("教师删除项目")
    public Response projectDelete(int id) {
        if (projectService.deleteProject(id)) {
            return Response.success();
        } else {
            return Response.fail("删除失败");
        }
    }

}
