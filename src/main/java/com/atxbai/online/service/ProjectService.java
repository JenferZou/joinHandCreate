package com.atxbai.online.service;

import com.atxbai.online.model.vo.ProjectPageReqVo;
import com.atxbai.online.model.vo.ProjectReqVo;
import com.atxbai.online.model.pojo.Project;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface ProjectService extends IService<Project> {

    void save(ProjectReqVo projectReqVo);

    /**
     * 列出所有项目信息
     * @param page    页码
     * @param limit   页大小
     * @param keyword 搜索关键字
     * @return
     */

    public Map<String, Object> listProject(int page, int limit, String keyword);

    /**
     * 修改项目信息
     * @param project 项目实体
     * @return
     */

    public boolean updateProject(Project project);
    /**
     * 删除项目信息
     * @param id 项目编号
     * @return
     */

   public boolean deleteProject(Integer id);
   /**
     * 批量删除项目信息
     * @param ids 项目编号
     * @return
     */

   public boolean multiDeleteProject(Integer[] ids);

    void saveProject(ProjectReqVo projectReqVo,String header);

    IPage<Project> pageQueryProject(ProjectPageReqVo pageReqVo,String header);


    void update(ProjectReqVo projectReqVo);
}

