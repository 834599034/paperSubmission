package com.example.papersubmission.config;

import io.goeasy.GoEasy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoEasyConfig {
    @Bean
    GoEasy goEasy(){
        GoEasy goEasy =new GoEasy("https://rest-hangzhou.goeasy.io", "BC-3f8da063d00d4983a29d0189ec2cdbe4");
        return goEasy;
    }
}
