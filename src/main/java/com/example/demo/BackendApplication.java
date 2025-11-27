package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// 1. Exclude the Spring Boot Error Configuration to prevent conflict with Tomcat
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class BackendApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        // 2. CRITICAL FIX: Explicitly disable the ErrorPageFilter registration.
        // This stops the "IllegalStateException: Failed to register filter" error.
        setRegisterErrorPageFilter(false);
        
        return application.sources(BackendApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}