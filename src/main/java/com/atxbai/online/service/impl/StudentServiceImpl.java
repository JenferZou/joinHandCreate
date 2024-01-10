package com.atxbai.online.service.impl;

import com.atxbai.online.mapper.StudentMapper;
import com.atxbai.online.model.pojo.Student;
import com.atxbai.online.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-09 15:05
 * @content: 学生实现类
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
