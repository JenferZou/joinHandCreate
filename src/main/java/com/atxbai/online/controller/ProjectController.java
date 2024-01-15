package com.atxbai.online.controller;

import com.atxbai.online.common.responseUtils.PageResponse;
import com.atxbai.online.common.responseUtils.Response;
import com.atxbai.online.common.responseUtils.ResponseCodeEnum;
import com.atxbai.online.exception.BizException;
import com.atxbai.online.model.vo.ProjectPageReqVo;
import com.atxbai.online.model.vo.ProjectReqVo;
import com.atxbai.online.model.pojo.Project;
import com.atxbai.online.service.ProjectService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/project")
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
    @GetMapping ("/getProject")
    public PageResponse<Project> getProject(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize){

        Page<Project> projectPage = new Page<>(pageNo == null ? 1 : pageNo, pageSize == null ? 10 : pageSize);
        LambdaQueryWrapper<Project> projectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        projectLambdaQueryWrapper.orderByDesc(Project::getStartTime);
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
    @GetMapping("/getProjectById")
    public PageResponse<Project> getProjectById(@RequestParam("id") Integer id, @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize){
        if(id == null){
            throw new BizException(ResponseCodeEnum.CODE_600);
        }
        Page<Project> projectPage = new Page<>(pageNo == null? 1 : pageNo, pageSize == null? 10 : pageSize);
        LambdaQueryWrapper<Project> projectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        projectLambdaQueryWrapper.eq(Project::getId, id);
        IPage<Project> page = projectService.page(projectPage, projectLambdaQueryWrapper);
        return PageResponse.success(page,page.getRecords());
    }


    @ApiOperation(value = "根据项目名称模糊查询获取项目详情")
    @GetMapping("/getProjectByNameLike")
    public PageResponse<Project> getProjectByNameLike(@RequestParam("name") String name, @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize){
        if(name == null){
            throw new BizException(ResponseCodeEnum.CODE_600);
        }
        Page<Project> projectPage = new Page<>(pageNo == null? 1 : pageNo, pageSize == null? 10 : pageSize);
        LambdaQueryWrapper<Project> projectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        projectLambdaQueryWrapper.like(Project::getName, name);
        IPage<Project> page = projectService.page(projectPage, projectLambdaQueryWrapper);
        return PageResponse.success(page,page.getRecords());
    }


    /**
     * 新增项目
     * @param projectReqVo
     * @return
     */
    @PostMapping("/saveProject")
    @ApiOperation("新增项目")
    public Response save(@RequestBody @Validated ProjectReqVo projectReqVo,@RequestHeader("Authorization") String header){
        log.info("新增项目{}",projectReqVo);
        projectService.saveProject(projectReqVo,header);
        return Response.success("操作成功");
    }

    /**
     * 项目分页查询
     * @param pageReqVo
     * @return
     */
    @PostMapping
    @ApiOperation("项目的分页查询")
    public PageResponse<Project> page(@RequestBody ProjectPageReqVo pageReqVo,@RequestHeader("Authorization") String header){
        log.info("传递进来的分页查询参数：{}",pageReqVo);
        IPage<Project> pageResult = projectService.pageQueryProject(pageReqVo, header);
        return PageResponse.success(pageResult,pageResult.getRecords());
    }

    /**
     * 根据id删除项目
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation("根据id删除项目")
    public Response delete(@PathVariable Integer id){
        log.info("根据id删除{}",id);
        projectService.deleteProject(id);
        return Response.success("操作成功");
    }

    @GetMapping("/getById")
    @ApiOperation("根据id查找项目")
    public Response getById( int id){
        log.info("根据id查找项目:{}",id);
        Project project = projectService.getById(id);
        return Response.success(project);
    }
    @PostMapping("/update")
    @ApiOperation("根据id修改项目")
    public Response update(@RequestBody @Validated ProjectReqVo projectReqVo){
        log.info("根据id修改项目:{}",projectReqVo);
        projectService.update(projectReqVo);
        return Response.success();
    }





}
