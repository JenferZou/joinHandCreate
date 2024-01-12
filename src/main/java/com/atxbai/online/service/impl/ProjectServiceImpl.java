package com.atxbai.online.service.impl;

import com.atxbai.online.common.securityUtils.JwtTokenHelper;
import com.atxbai.online.mapper.ProjectMapper;
import com.atxbai.online.model.pojo.Student;
import com.atxbai.online.model.vo.ProjectReqVo;
import com.atxbai.online.model.pojo.Project;
import com.atxbai.online.service.ProjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {
    @Resource
    private ProjectMapper projectMapper;

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    /**
     * 新增项目
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
    public Map<String, Object> listProject(int page, int limit,String keyword) {
        QueryWrapper<Project> wrapper=new QueryWrapper<>();
        if(keyword!=null&&!"".equals(keyword)&&keyword.length()>0){
            wrapper.like("name",keyword);
        }
        Long count = projectMapper.selectCount(wrapper);
        // 创建分页对象
        Page<Project> p = new Page<>(page, limit,count);
        // 执行分页查询
        IPage<Project> projectIPage = projectMapper.selectPage(p, wrapper);
        // 获取查询结果
        List<Project> projects = projectIPage.getRecords();
        Map<String,Object> map = new HashMap<>();
        map.put("projects",projects);
        map.put("total",projectIPage.getTotal());
        map.put("size",projectIPage.getSize());
        map.put("page",projectIPage.getPages());
        return map;
    }
}
