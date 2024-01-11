package com.atxbai.online.service.impl;

import com.atxbai.online.common.securityUtils.JwtTokenHelper;
import com.atxbai.online.mapper.ProjectMapper;
import com.atxbai.online.model.vo.ProjectReqVo;
import com.atxbai.online.model.pojo.Project;
import com.atxbai.online.service.ProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
