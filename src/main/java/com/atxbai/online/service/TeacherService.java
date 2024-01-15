package com.atxbai.online.service;

import com.atxbai.online.common.responseUtils.PageResponse;
import com.atxbai.online.common.responseUtils.Response;
import com.atxbai.online.model.pojo.Teacher;
import com.atxbai.online.model.vo.teacher.*;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-11 16:07
 * @content:
 */
public interface TeacherService {
    PageResponse getDeliever(GetDelieverReqVO getDelieverReqVO, String header);

    PageResponse searchRefuseDeliever(SearchNameRDReqVO searchNameRDReqVO, String header);

    /**
     * 拒绝学生申请
     * @param refuseDelieverReqVO
     * @param header
     * @return
     */
    Response refuseDeliever(RefuseDelieverReqVO refuseDelieverReqVO, String header);

    Response agreeDeliever(AgreeDelieverReqVO agreeDelieverReqVO, String header);

    /**
     * 列出教师信息
     * @param page 页码
     * @param limit 每页数量
     * @param keyword 查询关键字
     * @return
     */
   public Map<String, Object> listTeacher(int page, int limit, String keyword);
   /**
     * 根据no获取教师信息
     * @param no id信息
     * @return
     */

   public Teacher getTeacherByNo(Integer no);
   /**
     * 修改教师信息
     * @param teacher 修改实体
     * @return
     */

   public boolean updateTeacher(Teacher teacher);
   /**
     * 删除教师信息
     * @param id id信息
     * @return
     */

   public boolean deleteTeacher(Integer id);

    /**
     * 重置密码
     * @param id 老师id
     * @return
     */

   public boolean resetPassword(Integer id);

    /**
     * 添加老师
     * @param teacher 老师实体
     * @return
     */

   public boolean addTeacher(Teacher teacher);


    /**
     * 获取学生的个人和学生简历信息
     */
    Response lookStudentResume(LookStudentResumeReqVO lookStudentResumeReqVO);
}
