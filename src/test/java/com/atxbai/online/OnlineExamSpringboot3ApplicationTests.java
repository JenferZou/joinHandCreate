package com.atxbai.online;


import com.atxbai.online.common.securityUtils.JwtTokenHelper;
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

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

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

    @Test
    public void testToken(){
        String usernameByToken = jwtTokenHelper.getUsernameByToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMDAwMDEiLCJpc3MiOiJhdHhiYWkiLCJpYXQiOjE3MDQ5NTQxNTYsImV4cCI6MTcxMDEzODE1Nn0.iTz1370dc9g8mQR_Xj5-gWXMRgZM6dMkmVQ-qq003B8qbqx3iBN0uQevwEUEAqOnwfd7Tnx-9t5eJthP9f8DtA");
        System.out.println(usernameByToken);
    }
}
