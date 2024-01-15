package com.atxbai.online.controller;

import com.atxbai.online.common.constants.Constant;
import com.atxbai.online.common.responseUtils.PageResponse;
import com.atxbai.online.common.responseUtils.Response;
import com.atxbai.online.common.securityUtils.JwtTokenHelper;
import com.atxbai.online.model.pojo.DelieverResume;
import com.atxbai.online.model.pojo.Project;
import com.atxbai.online.model.pojo.Resume;
import com.atxbai.online.model.pojo.Student;
import com.atxbai.online.service.DelieverResumeService;
import com.atxbai.online.service.ProjectService;
import com.atxbai.online.service.ResumeService;
import com.atxbai.online.service.StudentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/delieverResume")
@Api(tags = "投递简历模块")
public class DelieverResumeController {

    @Autowired
    private DelieverResumeService delieverResumeService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private StudentService studentService;

    @ApiOperation(value = "根据pid获取投递的简历")
    @GetMapping("/getdelieverResumeByPid")
    public PageResponse<DelieverResume> getdelieverResumeByPid(@RequestParam("pid") Integer pid,@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize){
        Page<DelieverResume> delieverResumePage = new Page<>(pageNo == null ? 1 : pageNo, pageSize == null ? 10 : pageSize);
        LambdaQueryWrapper<DelieverResume> resumeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        resumeLambdaQueryWrapper.eq(DelieverResume::getPid, pid);
        IPage<DelieverResume> page = delieverResumeService.page(delieverResumePage, resumeLambdaQueryWrapper);
        return PageResponse.success(page, page.getRecords());


    }
    @ApiOperation(value = "根据tno获取投递的简历")
    @GetMapping("/getdelieverResumeBytno")
    public PageResponse<DelieverResume> getdelieverResumeBytno(@RequestParam("tno") Integer tno,@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize){
        Page<DelieverResume> delieverResumePage = new Page<>(pageNo == null ? 1 : pageNo, pageSize == null ? 10 : pageSize);
        LambdaQueryWrapper<DelieverResume> resumeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        resumeLambdaQueryWrapper.eq(DelieverResume::getTno,tno);
        IPage<DelieverResume> page = delieverResumeService.page(delieverResumePage, resumeLambdaQueryWrapper);
        return PageResponse.success(page, page.getRecords());
    }

    @ApiOperation(value = "投递简历")
    @PostMapping("/addDelieverResume")
    public Response addDelieverResume(@RequestBody Map<String, Object> map, @RequestHeader("Authorization") String userToken){
        Object id = map.get("id");
        Integer pid = Integer.parseInt(id.toString());
        String token = StringUtils.substring(userToken, 7);
        String sno = jwtTokenHelper.getUsernameByToken(token);
        DelieverResume delieverResume = new DelieverResume();
        Resume resume = resumeService.getOne(new LambdaUpdateWrapper<Resume>().eq(Resume::getSno, sno));
        Student student = studentService.getOne(new LambdaUpdateWrapper<Student>().eq(Student::getSno, sno));
        if(resume == null){
            return Response.fail("简历未创建，请先创建简历");
        }
        Project project = projectService.getById(pid);
        if(project == null){
            return Response.fail("项目不存在");
        }
        DelieverResume delieverDb = delieverResumeService.getOne(new LambdaQueryWrapper<DelieverResume>().eq(DelieverResume::getSno, sno).eq(DelieverResume::getPid, pid));
        if(delieverDb != null){
            return Response.fail("请勿多次投递");
        }
        delieverResume.setContent(project.getContent());
        delieverResume.setTno(project.getTno());
        delieverResume.setMark(Constant.RESUME_NO_AUDIT);
        delieverResume.setMentor(project.getMentor());
        delieverResume.setProjectName(project.getName());
        delieverResume.setSMajor(student.getSMajor());
        delieverResume.setSName(student.getSName());
        delieverResume.setSno(resume.getSno());
        delieverResume.setProjectName(project.getName());
        delieverResume.setPid(project.getId());
        boolean save = delieverResumeService.save(delieverResume);
        if(save){
            return Response.success("投递成功");
        }
        else {
            return Response.fail("投递失败");
        }

    }

    @ApiOperation(value = "根据sno获取投递的简历")
    @GetMapping("/getdelieverResumeBysno")
    public PageResponse<DelieverResume> getdelieverResumeBysno(@RequestHeader("Authorization") String userToken,@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize){
        String token = StringUtils.substring(userToken, 7);
        String sno = jwtTokenHelper.getUsernameByToken(token);
        Page<DelieverResume> delieverResumePage = new Page<>(pageNo == null ? 1 : pageNo, pageSize == null ? 10 : pageSize);
        LambdaQueryWrapper<DelieverResume> resumeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        resumeLambdaQueryWrapper.eq(DelieverResume::getSno,sno).orderByDesc(DelieverResume::getId);
        IPage<DelieverResume> page = delieverResumeService.page(delieverResumePage, resumeLambdaQueryWrapper);
        return PageResponse.success(page, page.getRecords());

    }

    @ApiOperation(value = "根据sno以及项目名称模糊查询获取投递的简历")
    @GetMapping("/getdelieverResumeBysnoAndName")
    public PageResponse<DelieverResume> getdelieverResumeBysnoAndName(@RequestHeader("Authorization") String userToken,@RequestParam("name")String name,@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize){
        String token = StringUtils.substring(userToken, 7);
        String sno = jwtTokenHelper.getUsernameByToken(token);
        Page<DelieverResume> delieverResumePage = new Page<>(pageNo == null ? 1 : pageNo, pageSize == null ? 10 : pageSize);
        LambdaQueryWrapper<DelieverResume> resumeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        resumeLambdaQueryWrapper.eq(DelieverResume::getSno,sno).like(DelieverResume::getProjectName,name);
        IPage<DelieverResume> page = delieverResumeService.page(delieverResumePage, resumeLambdaQueryWrapper);
        return PageResponse.success(page, page.getRecords());

    }


    @ApiOperation(value = "根据id删除投递的简历")
    @GetMapping("/deleteDelieverResumeById")
    public Response deleteDelieverResumeById(@RequestParam("id") Integer id){
        boolean remove = delieverResumeService.removeById(id);
        if(remove){
            return Response.success();
        }
        else {
            return Response.fail();
        }
    }



}
