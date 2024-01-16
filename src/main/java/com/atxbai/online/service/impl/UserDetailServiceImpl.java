package com.atxbai.online.service.impl;


import com.atxbai.online.mapper.LoginMapper;
import com.atxbai.online.mapper.ManagerMapper;
import com.atxbai.online.mapper.StudentMapper;
import com.atxbai.online.mapper.TeacherMapper;
import com.atxbai.online.model.pojo.Login;
import com.atxbai.online.model.pojo.Manager;
import com.atxbai.online.model.pojo.Student;
import com.atxbai.online.model.pojo.Teacher;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
@SuppressWarnings({"all"})
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private ManagerMapper managerMapper;

    @Autowired
    private LoginMapper loginMapper;

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
        } else if (Objects.isNull(student) && Objects.isNull(teacher)) {
            if (manager.getStatus().equals(1)){
                // 出现问题去认证失败处理器
                throw new UsernameNotFoundException("用户不存在");
            }
        }
        // 判断是哪个登录，教师，学生，管理员，返回认证信息
        if (Objects.nonNull(student)) {
            // 添加登录信息
            Login login = Login.builder().loginId(student.getSno()).loginName(student.getSName()).loginTime(LocalDate.now()).build();
            // 查询是否有今天登录的数据
            Login isLogin = loginMapper.selectOne(Wrappers.<Login>lambdaQuery()
                    .eq(Login::getLoginId, login.getLoginId())
                    .eq(Login::getLoginName,login.getLoginName())
                    .eq(Login::getLoginTime,login.getLoginTime())
            );
            if (Objects.isNull(isLogin)){
                // 插入数据
                loginMapper.insert(login);
            }
            // 返回学生
            return User.withUsername(student.getSno())
                   .password(student.getPassword())
                    // 用户鉴权
                   .authorities("ROLE_STUDENT")
                   .build();
        } else if (Objects.nonNull(teacher)) {
            LambdaQueryWrapper<Login> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Login::getLoginId,teacher.getNo());
            wrapper.eq(Login::getLoginName,teacher.getName());
            wrapper.eq(Login::getLoginTime,LocalDate.now());
            // 查询是否有今天登录的数据
            Login isLogin = loginMapper.selectOne(wrapper);

            if (Objects.isNull(isLogin)){
                // 添加登录信息
                Login login = Login.builder().loginId(teacher.getNo().toString()).loginName(teacher.getName()).loginTime(LocalDate.now()).build();
                // 插入数据
                loginMapper.insert(login);
            }
            // 返回教师
            return User.withUsername(teacher.getNo().toString())
                   .password(teacher.getPassword())
                    // 用户鉴权
                   .authorities("ROLE_TEACHER")
                   .build();
        }else {
            // 添加登录信息
            Login login = Login.builder().loginId(manager.getNo().toString()).loginName(manager.getName()).loginTime(LocalDate.now()).build();
            // 查询是否有今天登录的数据
            Login isLogin = loginMapper.selectOne(Wrappers.<Login>lambdaQuery()
                    .eq(Login::getLoginId, login.getLoginId())
                    .eq(Login::getLoginName,login.getLoginName())
                    .eq(Login::getLoginTime,login.getLoginTime())
            );
            if (Objects.isNull(isLogin)){
                // 插入数据
                loginMapper.insert(login);
            }
            // 返回管理员
            return User.withUsername(manager.getNo().toString())
                   .password(manager.getPassword())
                    // 用户鉴权
                   .authorities("ROLE_ADMIN")
                   .build();
        }

    }
}
