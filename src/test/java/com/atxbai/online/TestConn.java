package com.atxbai.online;

import com.atxbai.online.common.securityUtils.JwtTokenHelper;
import com.atxbai.online.mapper.ManagerMapper;
import com.atxbai.online.mapper.StudentMapper;
import com.atxbai.online.mapper.TeacherMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
public class TestConn {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ManagerMapper managerMapper;
    @Test
    public void testConnection() throws SQLException {
        System.out.println(this.studentMapper.findByUsername("100001"));
    }
    @Test
    public void testToken() {
        System.out.println(jwtTokenHelper.parseToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzMDAwMDAiLCJpc3MiOiJhdHhiYWkiLCJpYXQiOjE3MDQ5NTQyOTYsImV4cCI6MTcxMDEzODI5Nn0.T8viRsCTWqyvSe8shpbwB6tQ9AbUL4gw4Jqsl3TBEzhy4K1kwzHk5bYme70j6DdnwoI3y-ETAYBfCRjEtFjvQQ"));
    }
}
