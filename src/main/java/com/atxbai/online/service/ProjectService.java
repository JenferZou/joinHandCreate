package com.atxbai.online.service;

import com.atxbai.online.model.vo.ProjectReqVo;
import com.atxbai.online.model.pojo.Project;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface ProjectService extends IService<Project> {

    void save(ProjectReqVo projectReqVo);

    /**
     *
     * @param page
     * @param limit
     * @param keyword
     * @return
     */

   public Map<String, Object> listProject(int page, int limit,String keyword);
}
