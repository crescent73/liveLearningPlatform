package com.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@EnableTransactionManagement
@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class);
    }
    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize(DataSize.parse("10240KB")); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.parse("102400KB"));
        return factory.createMultipartConfig();
    }
}
