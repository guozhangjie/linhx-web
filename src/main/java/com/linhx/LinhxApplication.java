package com.linhx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.linhx.web.mapper")
public class LinhxApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinhxApplication.class, args);
    }

}
