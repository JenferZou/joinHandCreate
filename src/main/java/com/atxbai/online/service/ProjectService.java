package com.atxbai.online.service;

import com.atxbai.online.model.VO.ProjectVo;
import com.atxbai.online.model.pojo.Project;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ProjectService extends IService<Project> {

    void save(ProjectVo projectVo);
}
