package com.example.test.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler( "/files/**")
        .addResourceLocations("file:///C:/ProjectImage/");
    }

}