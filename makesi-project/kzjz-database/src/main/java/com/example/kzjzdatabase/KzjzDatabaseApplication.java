package com.example.kzjzdatabase;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@EnableSwagger2
//@EnableOpenApi
@SpringBootApplication
@MapperScan("com.example.kzjzdatabase.mapper")
public class KzjzDatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(KzjzDatabaseApplication.class, args);
    }

}
