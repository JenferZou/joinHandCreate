package com.atxbai.online.config.security;


import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author 小白
 * @version 1.0
 */
@Component
public class PasswordEncoderConfig {

    @Bean
    //PasswordEncoder 接口 Spring Security 提供的密码加密接口，它定义了密码加密和密码验证的方法
    public PasswordEncoder passwordEncoder(){
        // BCrypt 是一种安全且适合密码存储的哈希算法，它在进行哈希时会自动加入“盐”，增加密码的安全性。
        return new BCryptPasswordEncoder();
    }

    public static void main(String args[]){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
    }
}
