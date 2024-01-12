package com.atxbai.online.controller;

import com.atxbai.online.common.responseUtils.PageResponse;
import com.atxbai.online.common.responseUtils.Response;

import com.atxbai.online.model.vo.teacher.AgreeDelieverReqVO;
import com.atxbai.online.model.vo.teacher.GetDelieverReqVO;
import com.atxbai.online.model.vo.teacher.RefuseDelieverReqVO;
import com.atxbai.online.model.vo.teacher.SearchNameRDReqVO;
import com.atxbai.online.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
//@PreAuthorize("hasRole('ROLE_TEACHER')")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;


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
    @ApiOperation(value = "拒绝学生申请")
    public Response agreeDeliever(@RequestBody @Validated AgreeDelieverReqVO agreeDelieverReqVO, @RequestHeader("Authorization") String header){
        return teacherService.agreeDeliever(agreeDelieverReqVO,header);
    }
}
