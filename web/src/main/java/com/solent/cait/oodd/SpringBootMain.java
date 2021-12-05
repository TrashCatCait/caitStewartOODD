package com.solent.cait.oodd;

/*
 * @Author Cait
 * @Date 05-12-2021
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.solent.cait.oodd")
public class SpringBootMain extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringBootMain.class);
    }
            
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMain.class, args);
    }
}