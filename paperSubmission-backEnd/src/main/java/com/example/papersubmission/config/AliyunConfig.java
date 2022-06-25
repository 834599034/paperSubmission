package com.example.papersubmission.config;



import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
//@PropertySource(value = {"classpath:application-oss.properties"})
//@ConfigurationProperties(prefix = "alibaba")
@Data
public class AliyunConfig {

    @Value("${alibaba.cloud.oss.endpoint}")
    private String endpoint;
    @Value("${alibaba.cloud.access-key}")
    private String accessKeyId;
    @Value("${alibaba.cloud.secret-key}")
    private String accessKeySecret;
    @Value("${alibaba.cloud.oss.bucket}")
    private String bucketName;

    @Bean
    public OSS oSSClient() {
         // 创建OSSClient实例。
        return  new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

}