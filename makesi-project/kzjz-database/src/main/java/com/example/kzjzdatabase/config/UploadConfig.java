package com.example.kzjzdatabase.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
public class UploadConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize(DataSize.parse("1024000KB"));
        /// 总上传数据大小
        factory.setMaxRequestSize(DataSize.parse("1024000KB"));
        return factory.createMultipartConfig();
    }
}