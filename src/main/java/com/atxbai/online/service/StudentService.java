package com.atxbai.online.service;


import com.atxbai.online.model.pojo.Resume;
import com.atxbai.online.model.pojo.Student;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-09 15:05
 * @content:
 */
public interface StudentService extends IService<Student> {
    void add(Resume resume);

    void updateResume(Resume resume);
}
