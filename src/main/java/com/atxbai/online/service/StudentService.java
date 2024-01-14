package com.atxbai.online.service;


import com.atxbai.online.model.pojo.Resume;
import com.atxbai.online.model.pojo.Student;
import com.atxbai.online.model.vo.SearchDataVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-09 15:05
 * @content:
 */
public interface StudentService extends IService<Student> {
    /**
     *获取学生列表
     * @param page 当前页
     * @param limit 页面大小
     * @return
     */
    Map<String,Object> listStudent(int page, int limit);
    void add(Resume resume);

    void updateResume(Resume resume);

    /**
     * 修改学生信息
     * @param student 学生实体
     * @return
     */

   public boolean updateStudent(Student student);

    /**
     * 添加学生
     * @param student 学生信息
     * @return
     */
  public   boolean addStudent(Student student);

  public   boolean deleteStudent(Integer id);

    /**
     * 搜索
     * @param searchDataVO 搜索条件
     * @return
     */

  public   Map<String, Object> searchStudent(SearchDataVO searchDataVO);
  /**
     * 重置密码
     * @param id id
     * @return
     */

   public boolean resetPassword(Integer id);

    Student selectBySno(String sno);
}
