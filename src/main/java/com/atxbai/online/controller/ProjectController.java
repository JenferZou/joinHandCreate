package com.atxbai.online.controller;

import com.atxbai.online.common.copyUtils.CopyTools;
import com.atxbai.online.common.responseUtils.PageResponse;
import com.atxbai.online.common.responseUtils.Response;
import com.atxbai.online.common.responseUtils.ResponseCodeEnum;
import com.atxbai.online.exception.BizException;
import com.atxbai.online.model.VO.ProjectVo;
import com.atxbai.online.model.pojo.Project;
import com.atxbai.online.service.ProjectService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/teacher")
@Api(tags = "项目模块")
public class ProjectController {

    @Resource
    private ProjectService projectService;


    /**
     * 分页获取所有项目列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "获取所有项目")
    @GetMapping ("/getProject/{pageNo}/{pageSize}")
    public PageResponse<Project> getProject(@PathVariable("pageNo") Integer pageNo, @PathVariable("pageSize") Integer pageSize){

        Page<Project> projectPage = new Page<>(pageNo == null ? 1 : pageNo, pageSize == null ? 10 : pageSize);
        LambdaQueryWrapper<Project> projectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        projectLambdaQueryWrapper.orderByDesc(Project::getId);
        IPage<Project> page = projectService.page(projectPage, projectLambdaQueryWrapper);
        return PageResponse.success(page, page.getRecords());
    }


    /**
     * 根据项目id获取项目详情
     * @param id
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "根据项目id获取项目详情")

    @GetMapping("/getProjectById/{id}/{pageNo}/{pageSize}")
    public PageResponse<Project> getProjectById(@PathVariable("id") Integer id, @PathVariable("pageNo") Integer pageNo, @PathVariable("pageSize") Integer pageSize){
        if(id == null){
            throw new BizException(ResponseCodeEnum.CODE_600);
        }
        Page<Project> projectPage = new Page<>(pageNo == null? 1 : pageNo, pageSize == null? 10 : pageSize);
        LambdaQueryWrapper<Project> projectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        projectLambdaQueryWrapper.eq(Project::getId, id);
        IPage<Project> page = projectService.page(projectPage, projectLambdaQueryWrapper);
        return PageResponse.success(page,page.getRecords());
    }

    @PostMapping("/saveProject")
    @ApiOperation("新增项目")
    public Response save(@RequestBody @Validated ProjectVo projectVo){
        projectService.save(projectVo);
        return Response.success("发布成功");
    }







}
