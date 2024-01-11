package com.atxbai.online.controller;

import com.atxbai.online.common.copyUtils.CopyTools;
import com.atxbai.online.common.responseUtils.Response;
import com.atxbai.online.model.pojo.Student;
import com.atxbai.online.model.pojo.SearchDataVO;
import com.atxbai.online.model.vo.StudentVO;
import com.atxbai.online.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-10 17:22
 * @content:
 */
@Slf4j
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Api(tags = "管理员模块")
public class ManagerController {
    @Autowired
    private StudentService studentService;

/*    @PostMapping("/test/save")
    @ApiOperation(value = "测试接口")
    public Response test(@RequestBody @Validated com.atxbai.online.model.vo.TestSaveReqVO testSaveReqVO) {
        return Response.success(testSaveReqVO);
    }*/

    @GetMapping("/listStudent")
    @ApiOperation(value = "获取学生列表")
    public Response listStudent(int page, int limit) {
        if (page < 0 || limit < 0) {
            return Response.fail("参数错误");
        }
        Response<Map<String, Object>> data = new Response<>();
        Map<String, Object> d = studentService.listStudent(page, limit);
        data.setData(d);
        data.setErrorCode("200");
        return data;
    }

    @PostMapping("/modify")
    @ApiOperation(value = "修改学生信息")
    public Response modify(@RequestBody @Validated StudentVO studentVO) {
        Student student = CopyTools.copy(studentVO, Student.class);
        if (studentService.updateStudent(student)) {
            Response<String> data = new Response();
            data.setErrorCode("200");
            data.setMessage("修改成功");
            return data;
        } else {
            return Response.fail("修改失败");
        }
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加学生信息")
    public Response add(@RequestBody @Validated StudentVO studentVO) {
        Student student = CopyTools.copy(studentVO, Student.class);
        if (studentService.addStudent(student)) {
            Response<String> data = new Response();
            data.setErrorCode("200");
            data.setMessage("添加成功");
            return data;
        } else {
            return Response.fail("添加失败");
        }
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除学生信息")
    public Response delete(@RequestBody Integer id) {
        System.out.println(id);
        if (studentService.deleteStudent(id)) {
            Response<String> data = new Response();
            data.setErrorCode("200");
            data.setMessage("删除成功");
            return data;
        } else {
            return Response.fail("删除失败");
        }
    }

    @GetMapping("/search")
    @ApiOperation(value = "搜索学生信息")
    public Response search(@Validated SearchDataVO searchDataVO) {
        if (searchDataVO.getPage() < 0 || searchDataVO.getLimit() < 0) {
            return Response.fail("参数错误");
        }
        Response<Map<String, Object>> data = new Response<>();
        Map<String, Object> d = studentService.searchStudent(searchDataVO);
        data.setData(d);
        data.setErrorCode("200");
        return data;
    }

    @PostMapping("/modify/password")
    @ApiOperation(value = "修改密码")
    public Response modifyPassword(@RequestBody Map<String, String> data) {

        return null;
    }
}
