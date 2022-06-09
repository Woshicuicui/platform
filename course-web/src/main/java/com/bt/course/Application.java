package com.bt.course;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 启动类
@MapperScan("com.bt.course")
@SpringBootApplication(scanBasePackages = "com.bt.course")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
