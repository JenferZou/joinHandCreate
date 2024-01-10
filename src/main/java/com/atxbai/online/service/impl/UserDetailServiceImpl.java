package com.atxbai.online.service.impl;


import com.atxbai.online.mapper.ManagerMapper;
import com.atxbai.online.mapper.StudentMapper;
import com.atxbai.online.mapper.TeacherMapper;
import com.atxbai.online.model.pojo.Manager;
import com.atxbai.online.model.pojo.Student;
import com.atxbai.online.model.pojo.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author 小白
 * @version 1.0
 * create: 2023-12-28 12:01
 * content: UserDetailsService 接口
 *     用于代替 springSecurity 默认的内存中查找，
 *     通过重写，我们可以自定义从应用程序的数据源（如数据库、LDAP、内存等）中加载用户信息
 */
@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private ManagerMapper managerMapper;


    /**
     * 完成登录的效验，在 springSecurity 的 UserDetails 对象中
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // ========== 数据库中查找用户是否存在 ================
        Student student = studentMapper.findByUsername(username);
        Teacher teacher = teacherMapper.findByUsername(username);
        Manager manager = managerMapper.findByUsername(username);
        // 三个都为空
        if (Objects.isNull(student) && Objects.isNull(teacher) && Objects.isNull(manager)) {
            // 出现问题去认证失败处理器
            throw new UsernameNotFoundException("用户不存在");
        }

        // 判断是哪个登录，教师，学生，管理员，返回认证信息
        if (Objects.nonNull(student)) {
            // 返回学生
            return User.withUsername(student.getSno())
                   .password(student.getPassword())
                    // 用户鉴权
                   .authorities("ROLE_STUDENT")
                   .build();
        } else if (Objects.nonNull(teacher)) {
            // 返回教师
            return User.withUsername(teacher.getNo())
                   .password(teacher.getPassword())
                    // 用户鉴权
                   .authorities("ROLE_TEACHER")
                   .build();
        }else {
            // 返回管理员
            return User.withUsername(manager.getNo())
                   .password(manager.getPassword())
                    // 用户鉴权
                   .authorities("ROLE_ADMIN")
                   .build();
        }

    }
}
