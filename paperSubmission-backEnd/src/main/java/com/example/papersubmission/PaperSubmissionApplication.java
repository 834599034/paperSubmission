package com.example.papersubmission;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.cors.reactive.CorsWebFilter;

@SpringBootApplication
public class PaperSubmissionApplication {


    public static void main(String[] args) {
        SpringApplication.run(PaperSubmissionApplication.class, args);
        

    }

}
