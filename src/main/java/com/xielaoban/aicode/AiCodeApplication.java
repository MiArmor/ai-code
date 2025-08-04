package com.xielaoban.aicode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
@MapperScan("com.xielaoban.aicode.mapper")
public class AiCodeApplication {
    public static void main(String[] args) {
        SpringApplication.run(AiCodeApplication.class, args);
    }

}
