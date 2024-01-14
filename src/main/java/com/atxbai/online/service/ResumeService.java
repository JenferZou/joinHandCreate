package com.atxbai.online.service;

import com.atxbai.online.model.pojo.Resume;
import com.atxbai.online.model.pojo.Student;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Mr.LL
 * @version 1.0
 */
public interface ResumeService extends IService<Resume> {


    Resume selectBySno(String sno);


    void updateResume(Resume resume);
}