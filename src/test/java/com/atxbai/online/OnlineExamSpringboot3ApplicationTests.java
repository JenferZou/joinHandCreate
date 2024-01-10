package com.atxbai.online;


import com.atxbai.online.mapper.ManagerMapper;
import com.atxbai.online.mapper.StudentMapper;
import com.atxbai.online.mapper.TeacherMapper;
import com.atxbai.online.model.pojo.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class OnlineExamSpringboot3ApplicationTests {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private ManagerMapper managerMapper;



    @Test
    public void testConnection() {
        List<Student> students = studentMapper.selectList(null);
        students.forEach(System.out::println);

        teacherMapper.selectList(null).forEach(System.out::println);

        managerMapper.selectList(null).forEach(System.out::println);

    }

    @Test
    public void testStudent() {
        Student byUsername = studentMapper.findByUsername("100001");
        System.out.println(byUsername);

    }
}
