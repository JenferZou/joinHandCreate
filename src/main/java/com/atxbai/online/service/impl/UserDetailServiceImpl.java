package com.atxbai.online.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 完成登录的效验，在 springSecurity 的 UserDetails 对象中
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // ========== 数据库中查找用户是否存在 ================
        UserDO userDO = userMapper.findByUsername(username);

        log.warn("对象是否存在"+userDO+" 传入用户名:"+username);
        // 判断用户是否存在
        if(Objects.isNull(userDO)){
            // 出现问题去认证失败处理器
            throw new UsernameNotFoundException("用户不存在");
        }

        // ============== 查询用户的角色 ====================
        List<UserRoleDO> roleDOS = userRoleMapper.selectByUsername(username);

        String[] roleArr = null;

        // 用户角色转数组
        if (!CollectionUtils.isEmpty(roleDOS)) {
            List<String> roles = roleDOS.stream().map(p -> p.getRole()).collect(Collectors.toList());
            // 将对象强转
            roleArr = roles.toArray(new  String[roles.size()]);
        }


        // authorities 用于指定角色
        // User 对象实现了 UserDetails 对象
        return User.withUsername(userDO.getUsername())
                .password(userDO.getPassword())
                // 用户鉴权
                .authorities(roleArr)
                .build();
    }
}
