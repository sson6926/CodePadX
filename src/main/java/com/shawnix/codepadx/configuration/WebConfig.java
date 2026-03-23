package com.shawnix.codepadx.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")   // tạm: cho tất cả
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(false); // nếu dùng cookie thì phải để true + không dùng "*"
    }
}