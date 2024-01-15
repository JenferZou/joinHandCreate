package com.atxbai.online.service;

import com.atxbai.online.common.responseUtils.Response;

import java.util.Map;

public interface ManagerService {
    /**
     * 列出所有管理员信息
     * @param page 页码
     * @param limit 页大小
     * @param keyword 关键字
     * @return
     */
   public Map<String, Object> listManager(int page, int limit, String keyword,String header);
   /**
     * 删除管理员
     * @param id id
     * @return
     */
   public boolean deleteManager(Integer id);

    Response dauList();

    Response stfList();
}
