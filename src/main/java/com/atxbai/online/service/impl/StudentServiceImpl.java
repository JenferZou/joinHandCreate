package com.atxbai.online.service.impl;

import com.atxbai.online.mapper.ResumeMapper;
import com.atxbai.online.mapper.StudentMapper;
import com.atxbai.online.model.pojo.Resume;
import com.atxbai.online.model.pojo.Student;
import com.atxbai.online.model.vo.SearchDataVO;
import com.atxbai.online.service.StudentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-09 15:05
 * @content: 学生实现类
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ResumeMapper resumeMapper;

    @Override
    public Map<String, Object> listStudent(int page, int limit) {
        Long count = studentMapper.selectCount(null);
        // 创建分页对象
        Page<Student> p = new Page<>(page, limit, count);

        // 执行分页查询
        IPage<Student> studentPage = studentMapper.selectPage(p, null);
        // 获取查询结果
        List<Student> students = studentPage.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("students", students);
        map.put("total", studentPage.getTotal());
        map.put("size", studentPage.getSize());
        map.put("page", studentPage.getPages());
        return map;
    }

    @Override
    public void add(Resume resume) {
        studentMapper.add(resume);
    }

    @Override
    public void updateResume(Resume resume) {
        studentMapper.updateResume(resume);
    }

    @Override
    public boolean updateStudent(Student student) {
        return studentMapper.updateStudent(student) >= 1;
    }

    @Override
    public boolean addStudent(Student student) {
        student.setPassword(passwordEncoder.encode("123456"));
        return studentMapper.insert(student) >= 1;
    }

    @Override
    public boolean deleteStudent(Integer id) {
        return studentMapper.deleteById(id) >= 1;
    }

    @Override
    public Map<String, Object> searchStudent(SearchDataVO searchDataVO) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        if (searchDataVO.getKeyword().length() != 0) {
            wrapper.like("sName", searchDataVO.getKeyword()).or().eq("sno", searchDataVO.getKeyword());
        }
        if (searchDataVO.getClassName().length() != 0) {
            wrapper.eq("className", searchDataVO.getClassName());
        }
        if (searchDataVO.getSDepartment().length() != 0) {
            wrapper.eq("sDepartment", searchDataVO.getSDepartment());
        }
        if (searchDataVO.getSMajor().length() != 0) {
            wrapper.eq("sMajor", searchDataVO.getSMajor());
        }
        Long count = studentMapper.selectCount(wrapper);
        // 创建分页对象
        Page<Student> p = new Page<>(searchDataVO.getPage(), searchDataVO.getLimit(), count);

        // 执行分页查询
        IPage<Student> studentPage = studentMapper.selectPage(p, wrapper);
        // 获取查询结果
        List<Student> students = studentPage.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("students", students);
        map.put("total", studentPage.getTotal());
        map.put("size", studentPage.getSize());
        map.put("page", studentPage.getPages());
        return map;
    }

    @Override
    public boolean resetPassword(Integer id) {
        String password = passwordEncoder.encode("123456");
        return studentMapper.resetPassword(password, id);
    }

    @Override
    public Student selectBySno(String sno) {
        Student student = studentMapper.selectBySno(sno);
        return student;
    }

    @Override
    public List<Student> exportStudentExcel() {
        return studentMapper.selectList(null);
    }

    @Override
    public int saveMore(List<Student> students) {
        int i=0;
        for(Student student:students){
            if(!Objects.nonNull(studentMapper.selectBySno(student.getSno()))) {
                student.setPassword(passwordEncoder.encode("123456"));
                i += studentMapper.insert(student);
            }
        }
        return i;
    }

    @Override
    public Map<String, Object> findStudentAndResume(String sno) {
        Map<String, Object> map = new HashMap<>();
        Student student = studentMapper.findByUsername(sno);
        map.put("student", student);
        Resume resume =resumeMapper.selectBySno(sno);

        map.put("resume", resume);
        return map;
    }
}
