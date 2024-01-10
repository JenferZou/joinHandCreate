package com.atxbai.online;

import com.atxbai.online.model.DO.StudentDO;
import com.atxbai.online.mapper.StudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class OnlineExamSpringboot3ApplicationTests {

    @Autowired
    private StudentMapper mapper;

    @Test
    public void testConnection() {
        List<StudentDO> studentDOS = mapper.selectList(null);
        studentDOS.forEach(System.out::println);
    }

}
