package com.atxbai.online.service.impl;

import com.atxbai.online.mapper.ResumeMapper;
import com.atxbai.online.mapper.StudentMapper;
import com.atxbai.online.model.pojo.Resume;
import com.atxbai.online.model.pojo.Student;
import com.atxbai.online.service.ResumeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Mr.LL
 * @version 1.0
 */
@Service
public class ResumeServiceImpl extends ServiceImpl<ResumeMapper, Resume> implements ResumeService {

    @Resource
    private ResumeMapper resumeMapper;
    @Override
    public Resume selectBySno(String sno) {
         Resume resume = resumeMapper.selectBySno(sno);
         return resume;
    }

    @Override
    public void updateResume(Resume resume) {
        resumeMapper.updateById(resume);
    }
}
