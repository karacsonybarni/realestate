package com.realestate.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Handle .well-known directory
        registry.addResourceHandler("/.well-known/**")
                .addResourceLocations("classpath:/.well-known/");

        // Handle privacy.md
        registry.addResourceHandler("/privacy/**")
                .addResourceLocations("classpath:/privacy/");
    }
}
