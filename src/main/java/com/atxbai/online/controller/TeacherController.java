package com.atxbai.online.controller;

import com.atxbai.online.common.responseUtils.Response;
import com.atxbai.online.model.vo.TestSaveReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/test/save")
    @ApiOperation(value = "测试接口")
    public Response test(@RequestBody @Validated TestSaveReqVO testSaveReqVO){
        return Response.success(testSaveReqVO);
    }
}
