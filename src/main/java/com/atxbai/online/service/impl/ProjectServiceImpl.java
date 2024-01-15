package com.atxbai.online.service.impl;

import com.atxbai.online.common.securityUtils.JwtTokenHelper;
import com.atxbai.online.common.textUtils.HtmlFilterHelper;
import com.atxbai.online.mapper.ProjectMapper;
import com.atxbai.online.model.pojo.Project;
import com.atxbai.online.model.vo.ProjectPageReqVo;
import com.atxbai.online.model.vo.ProjectReqVo;
import com.atxbai.online.service.ProjectService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {
    @Resource
    private ProjectMapper projectMapper;

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    /**
     * 新增项目
     *
     * @param projectReqVo
     */
    @Override
    public void save(ProjectReqVo projectReqVo) {
        Project project = new Project();

        //对象属性拷贝
        BeanUtils.copyProperties(projectReqVo, project);

        // 授权对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 获取当前对象的用户名
        int name = Integer.parseInt(authentication.getName());
        project.setTno(name);

        projectMapper.insert(project);
    }

    @Override
    public Map<String, Object> listProject(int page, int limit, String keyword) {
        QueryWrapper<Project> wrapper = new QueryWrapper<>();
        if (keyword != null && !"".equals(keyword) && keyword.length() > 0) {
            wrapper.like("name", keyword);
        }
        Long count = projectMapper.selectCount(wrapper);
        // 创建分页对象
        Page<Project> p = new Page<>(page, limit, count);
        // 执行分页查询
        IPage<Project> projectIPage = projectMapper.selectPage(p, wrapper);
        // 获取查询结果
        List<Project> projects = projectIPage.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("projects", projects);
        map.put("total", projectIPage.getTotal());
        map.put("size", projectIPage.getSize());
        map.put("page", projectIPage.getPages());
        return map;
    }

    @Override
    public boolean updateProject(Project project) {
        return projectMapper.updateById(project) >= 1;
    }

    @Override
    public boolean deleteProject(Integer id) {
        return projectMapper.deleteById(id) >= 1;
    }

    @Override
    public boolean multiDeleteProject(Integer[] ids) {
        return projectMapper.deleteBatchIds(Arrays.asList(ids)) >= 1;
    }

    /**
     * 新增项目
     *
     * @param projectReqVo
     */
    @Override
    public void saveProject(ProjectReqVo projectReqVo, String header) {
        Project project = new Project();

        //对象属性拷贝
        BeanUtils.copyProperties(projectReqVo, project);

        // 解析 token
        String token = StringUtils.substring(header, 7);
        Integer tno = Integer.valueOf(jwtTokenHelper.getUsernameByToken(token));
        // 设置教师 tno
        project.setTno(tno);

        projectMapper.insert(project);
    }

    /**
     * 项目分页
     *
     * @param pageReqVo
     * @return
     */
    @Override
    public IPage<Project> pageQueryProject(ProjectPageReqVo pageReqVo, String header) {
        //分页参数
        Page<Project> projectPage = new Page<>(pageReqVo.getPage(), pageReqVo.getPageSize());

        // 解析 token
        String token = StringUtils.substring(header, 7);
        String tno = jwtTokenHelper.getUsernameByToken(token);

        LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<>();
        //只查询当前登录老师的项目
        queryWrapper.eq(Project::getTno, tno);

        //条件查询
        if (pageReqVo.getName() != null && !pageReqVo.getName().isEmpty()) {
            queryWrapper.like(Project::getName, pageReqVo.getName());
        }
        if (pageReqVo.getStartTime() != null) {
            //大于等于
            queryWrapper.ge(Project::getStartTime, pageReqVo.getStartTime());
        }
        if (pageReqVo.getNeedMajor() != null && !pageReqVo.getNeedMajor().isEmpty()) {
            queryWrapper.like(Project::getNeedMajor, pageReqVo.getNeedMajor());
        }
        if (pageReqVo.getExpectedCompetition() != null && !pageReqVo.getExpectedCompetition().isEmpty()) {
            queryWrapper.like(Project::getExpectedCompetition, pageReqVo.getExpectedCompetition());
        }
        IPage<Project> projectIPage = projectMapper.selectPage(projectPage, queryWrapper);
        List<Project> records = projectPage.getRecords();
        if (Objects.nonNull(records)){
            records = records.stream().map(project -> {
                String content = HtmlFilterHelper.getContent(project.getContent());
                project.setContent(content);
                return project;
            }).collect(Collectors.toList());
        }
        projectPage.setRecords(records);
        log.warn("数据:{}",records);
        return projectIPage;
    }


    @Override
    public void update(ProjectReqVo projectReqVo) {
        Project project = new Project();
        //对象属性拷贝
        BeanUtils.copyProperties(projectReqVo, project);
        projectMapper.updateById(project);
    }

}
