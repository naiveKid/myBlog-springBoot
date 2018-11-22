package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
// 使用@ServletComponentScan开启注解
// Servlet、Filter、Listener可以直接通过@WebServlet、@WebFilter、@WebListener注解自动注册
@ServletComponentScan
//开启dubbo
@MapperScan(value="com.core.dao")
public class MyblogApplication {
    public static void main(String[] args) {
        //完全禁用热部署重启
        //System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(MyblogApplication.class);
    }
}