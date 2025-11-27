package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import jakarta.servlet.Filter;

// 1. Exclude Error Page Filter to prevent conflict with Tomcat
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class BackendApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        // 2. Explicitly disable the ErrorPageFilter registration
        setRegisterErrorPageFilter(false);
        return application.sources(BackendApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    // 3. CRITICAL FIX: Prevent "springSecurityFilterChain already registered" error.
    // This tells Spring Boot: "Tomcat already found the security filter, don't register it again."
    @Bean
    public FilterRegistrationBean<Filter> securityFilterChainRegistration(Filter springSecurityFilterChain) {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>(springSecurityFilterChain);
        registration.setName("springSecurityFilterChain");
        registration.setEnabled(false); // This stops the crash!
        return registration;
    }
}