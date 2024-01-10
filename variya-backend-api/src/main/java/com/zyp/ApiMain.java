package com.zyp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zyp.mapper")
public class ApiMain {
    public static void main(String[] args) {
        SpringApplication.run(ApiMain.class,args);
    }
}
