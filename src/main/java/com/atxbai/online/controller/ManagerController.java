package com.atxbai.online.controller;

import com.atxbai.online.common.copyUtils.CopyTools;
import com.atxbai.online.common.responseUtils.Response;
import com.atxbai.online.model.pojo.Project;
import com.atxbai.online.model.pojo.Resume;
import com.atxbai.online.model.pojo.Student;
import com.atxbai.online.model.pojo.Teacher;
import com.atxbai.online.model.vo.ProjectReqVo;
import com.atxbai.online.model.vo.SearchDataVO;
import com.atxbai.online.model.vo.StudentVO;
import com.atxbai.online.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
//@PreAuthorize("hasRole('ROLE_ADMIN')")
@Api(tags = "管理员模块")
public class ManagerController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ResumeService resumeService;


    @GetMapping("/dauList")
    @ApiOperation(value = "获取日活信息")
    public Response dauList() {
        return managerService.dauList();
    }

    @GetMapping("/STFInfo")
    @ApiOperation(value = "获取师生比信息")
    public Response stfList() {
        return managerService.stfList();
    }

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

    @PostMapping("/student/changePassword")
    @ApiOperation(value = "修改密码")
    public Object changePassword(@RequestBody Integer id) {
        if (studentService.resetPassword(id)) {
            Response<String> data = new Response<>();
            data.setErrorCode("200");
            data.setMessage("重置成功,密码为123456");
            return data;
        } else {
            return Response.fail("重置失败");
        }
    }

    @PostMapping("/modify/password")
    @ApiOperation(value = "修改密码")
    public Response modifyPassword(@RequestBody Map<String, String> data) {

        return null;
    }

    @GetMapping("/project/list")
    @ApiOperation(value = "获取项目列表")
    public Response projectList(int page, int limit, String keyword) {
        if (page < 0 || limit < 0) {
            return Response.fail("参数错误");
        }
        Response<Map<String, Object>> data = new Response<>();
        Map<String, Object> d = projectService.listProject(page, limit, keyword);
        data.setData(d);
        data.setErrorCode("200");
        return data;
    }

    @PostMapping("/project/modify")
    @ApiOperation(value = "修改项目信息")
    public Object projectModify(@RequestBody Project project) {
        if (projectService.updateProject(project)) {
            Response<String> data = new Response<>();
            data.setErrorCode("200");
            data.setMessage("修改成功");
            return data;
        } else {
            return Response.fail("修改失败");
        }
    }

    @PostMapping("/project/delete")
    @ApiOperation(value = "删除项目信息")
    public Object projectDelete(@RequestBody Integer id) {
        if (projectService.deleteProject(id)) {
            Response<String> data = new Response<>();
            data.setErrorCode("200");
            data.setMessage("删除成功");
            return data;
        } else {
            return Response.fail("删除失败");
        }
    }

    @PostMapping("/project/multidelete")
    @ApiOperation(value = "批量删除项目信息")
    public Object projectDelete(@RequestBody Integer[] ids) {
        if (projectService.multiDeleteProject(ids)) {
            Response<String> data = new Response<>();
            data.setErrorCode("200");
            data.setMessage("删除成功");
            return data;
        } else {
            return Response.fail("删除失败");
        }
    }

    @GetMapping("/project/get/{id}")
    @ApiOperation(value = "获取项目信息")
    public Object projectGet(@PathVariable Integer id) {
        Project project = projectService.getById(id);
        if (project == null) {
            return Response.fail("项目不存在");
        }
        Response<Project> data = new Response<>();
        data.setData(project);
        data.setErrorCode("200");
        return data;
    }

    @GetMapping("/manager/list")
    @ApiOperation(value = "获取管理员信息")
    public Object managerGet(int page, int limit, String keyword, @RequestHeader("Authorization") String header) {
        if (page < 0 || limit < 0) {
            return Response.fail("参数错误");
        }
        Response<Map<String, Object>> data = new Response<>();
        Map<String, Object> d = managerService.listManager(page, limit, keyword, header);
        data.setData(d);
        data.setErrorCode("200");
        return data;
    }

    @PostMapping("/manager/delete")
    @ApiOperation(value = "删除管理员信息")
    public Object managerDelete(@RequestBody Integer id) {
        if (managerService.deleteManager(id)) {
            Response<String> data = new Response<>();
            data.setErrorCode("200");
            data.setMessage("删除成功");
            return data;
        } else {
            return Response.fail("删除失败");
        }
    }

    @GetMapping("/teacher/list")
    @ApiOperation(value = "获取教师信息")
    public Object teacherGet(int page, int limit, String keyword) {
        if (page < 0 || limit < 0) {
            return Response.fail("参数错误");
        }
        Response<Map<String, Object>> data = new Response<>();
        Map<String, Object> d = teacherService.listTeacher(page, limit, keyword);
        data.setData(d);
        data.setErrorCode("200");
        return data;
    }

    @GetMapping("/teacher/preedit/{no}")
    @ApiOperation(value = "获取单个教师信息")
    public Object teacherPreedit(@PathVariable(value = "no") Integer no) {
        Response<Teacher> data = new Response<>();
        Teacher t = teacherService.getTeacherByNo(no);
        data.setData(t);
        data.setErrorCode("200");
        return data;
    }

    @PostMapping("/teacher/modify")
    @ApiOperation(value = "修改教师信息")
    public Object teacherModify(@RequestBody Teacher teacher) {
        if (teacherService.updateTeacher(teacher)) {
            Response<String> data = new Response<>();
            data.setErrorCode("200");
            data.setMessage("修改成功");
            return data;
        } else {
            return Response.fail("修改失败");
        }
    }

    @PostMapping("/teacher/delete")
    @ApiOperation(value = "删除教师信息")
    public Object teacherDelete(@RequestBody Integer id) {
        if (teacherService.deleteTeacher(id)) {
            Response<String> data = new Response<>();
            data.setErrorCode("200");
            data.setMessage("删除成功");
            return data;
        } else {
            return Response.fail("删除失败");
        }
    }

    @PostMapping("/teacher/add")
    @ApiOperation(value = "添加教师信息")
    public Object teacherAdd(@RequestBody Teacher teacher) {
        if (teacherService.addTeacher(teacher)) {
            Response<String> data = new Response<>();
            data.setErrorCode("200");
            data.setMessage("添加成功");
            return data;
        } else {
            return Response.fail("添加失败");
        }
    }

    @PostMapping("/teacher/resetPassword")
    @ApiOperation(value = "重置教师密码")
    public Object teacherResetPassword(@RequestBody Integer no) {
        if (teacherService.resetPassword(no)) {
            Response<String> data = new Response<>();
            data.setErrorCode("200");
            data.setMessage("重置成功,密码为888888");
            return data;
        } else {
            return Response.fail("重置失败");
        }
    }

    @GetMapping("/preview/resume/{sno}")
    @ApiOperation(value = "预览简历及学生信息")
    public Object previewResume(@PathVariable("sno") String sno) {
        Response<Map<String, Object>> data = new Response<>();
        Map<String, Object> studentAndResume = studentService.findStudentAndResume(sno);
        data.setData(studentAndResume);
        data.setErrorCode("200");
        return data;
    }

    @PostMapping("/resume/update")
    @ApiOperation(value = "更新简历")
    public Object resumeUpdate(@RequestBody Resume resume) {
        if (resume.getResumeId() != null && resumeService.updateById(resume)) {
            Response<String> data = new Response<>();
            data.setErrorCode("200");
            data.setMessage("更新成功");
            return data;
        } else {
            return Response.fail("更新失败");
        }
    }
}
