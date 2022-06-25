package com.example.papersubmission.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class WebCorsConfiguration {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }


//    @Bean
//    public CorsWebFilter corsWebFilter() {
//        org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource();
//
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//
//        //1、配置跨域
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.setAllowCredentials(true);
//
//        source.registerCorsConfiguration("/*",corsConfiguration);
//        return new CorsWebFilter(source);
//    }
}


