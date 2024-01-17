package com.atxbai.online.controller;

import com.atxbai.online.common.copyUtils.CopyTools;
import com.atxbai.online.common.excel.ExcelHandle;
import com.atxbai.online.common.excel.ExportExcelUtils;
import com.atxbai.online.common.responseUtils.Response;
import com.atxbai.online.model.pojo.*;
import com.atxbai.online.model.vo.ProjectReqVo;
import com.atxbai.online.model.vo.SearchDataVO;
import com.atxbai.online.model.vo.StudentVO;
import com.atxbai.online.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-10 17:22
 * @content:
 */
@Slf4j
@RestController
@RequestMapping("/admin")

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
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;
    @Value("${qiniu.bucket.name}")
    private String bucketName;
    @Value("${qiniu.bucket.url}")
    private String bucketUrl;
    @GetMapping("upLoadToken")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
    public Object getUpLoadToken() {
        Response<Object> data = new Response();
        Map<String, Object> res = new HashMap<>();
        //上传文件名称
        String fileName = UUID.randomUUID().toString();
        data.setErrorCode("200");
        //生成上传凭证
        Auth auth = Auth.create(accessKey, secretKey);
        StringMap putPolicy = new StringMap();
        try {
            Map<String, Integer> map = new HashMap<>();
            map.put("code",0);
            putPolicy.put("returnBody",this.objectMapper.writeValueAsString(map));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String upToken = auth.uploadToken(bucketName, fileName,3600,putPolicy);
        res.put("upToken", upToken);
        res.put("fileName", fileName);
        res.put("url", bucketUrl +"/"+ fileName);
        data.setData(res);
        return data;
    }

    @GetMapping("/dauList")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "获取日活信息")
    public Response dauList() {
        return managerService.dauList();
    }

    @GetMapping("/STFInfo")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "获取师生比信息")
    public Response stfList() {
        return managerService.stfList();
    }

    @GetMapping("/listStudent")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "添加学生信息")
    public Response add(@RequestBody @Validated StudentVO studentVO) {
        Student student = CopyTools.copy(studentVO, Student.class);
        return studentService.addStudent(student);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "删除学生信息")
    public Response delete(@RequestBody Integer id) {
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "修改密码")
    public Response modifyPassword(@RequestBody Map<String, String> data) {

        return null;
    }

    @GetMapping("/project/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "删除项目信息")
    public Object projectDelete(@RequestBody Integer id) {
        System.out.println(id);
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @PostMapping("/manager/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "修改管理员信息")
    public Object managerUpdate(@RequestBody Manager manager) {
        if (managerService.updateManager(manager)) {
            Response<String> data = new Response<>();
            data.setErrorCode("200");
            data.setMessage("修改成功");
            return data;
        } else {
            return Response.fail("修改失败");
        }
    }

    @GetMapping("/teacher/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object teacherPreedit(@PathVariable(value = "no") Integer no) {
        Response<Teacher> data = new Response<>();
        Teacher t = teacherService.getTeacherByNo(no);
        data.setData(t);
        data.setErrorCode("200");
        return data;
    }

    @PostMapping("/teacher/modify")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "预览简历及学生信息")
    public Object previewResume(@PathVariable("sno") String sno) {
        Response<Map<String, Object>> data = new Response<>();
        Map<String, Object> studentAndResume = studentService.findStudentAndResume(sno);
        data.setData(studentAndResume);
        data.setErrorCode("200");
        return data;
    }

    @PostMapping("/resume/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @GetMapping(value = "/excel/exportBankCheckInfo")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "导出学生信息")
    public void ExportBankCkeckInfo(HttpServletResponse response, HttpServletRequest request) {
        //得到所有要导出的数据
        List<Student> students = studentService.exportStudentExcel();
        //定义导出的excel名字
        String excelName = "学生表";
        //获取需要转出的excel表头的map字段
        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>();
        fieldMap.put("id", "编号");
        fieldMap.put("sName", "姓名");
        fieldMap.put("sno", "学号");
        fieldMap.put("gender", "性别");
        fieldMap.put("sMajor", "专业");
        fieldMap.put("className", "班级");
        fieldMap.put("sDepartment", "所属学院");
        fieldMap.put("sPhone", "电话");
        //fieldMap.put("简历","resumeId");
        //导出用户相关信息
        new ExportExcelUtils();
        ExportExcelUtils.export(excelName, students, fieldMap, response);
    }

    @PostMapping("/excel/leadExcel")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "导出学生信息")
    public Object leadExcel(@RequestBody MultipartFile file) {
        int i = 0;
        if (file == null) {
            return Response.fail("文件为空");
        }
        try {
            InputStream inputStream = file.getInputStream();
            LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>();
            fieldMap.put("姓名", "SName");
            fieldMap.put("学号", "sno");
            fieldMap.put("性别", "gender");
            fieldMap.put("专业", "SMajor");
            fieldMap.put("班级", "className");
            fieldMap.put("所属学院", "SDepartment");
            fieldMap.put("电话", "SPhone");
            List<Student> students = new ExcelHandle().handlerData(inputStream, fieldMap, Student.class);
            i = studentService.saveMore(students);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (i > 0) {
            Response<String> data = new Response<>();
            data.setErrorCode("200");
            data.setMessage("上传成功");
            return data;
        } else {
            return Response.fail("上传失败");
        }
    }

    @ApiOperation("上传Excel导入教师")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/excel/upload")
    public Response upload(@RequestParam(value = "file", required = true) MultipartFile file) throws IOException {
        teacherService.upload(file.getInputStream());
        return Response.success();
    }

    @ApiOperation("下载 Excel 导出教师")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/excel/download")
    public Response download() {
        teacherService.export();
        return Response.success();
    }
}
