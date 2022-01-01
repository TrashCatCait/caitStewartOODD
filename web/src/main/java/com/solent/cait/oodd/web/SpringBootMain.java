package com.solent.cait.oodd.web;

/*
 * @Author Cait
 * @Date 05-12-2021
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 
 * @author caitlyn
 * Spring boot application start point
 */
@SpringBootApplication(scanBasePackages = "com.solent.cait.oodd.web")
public class SpringBootMain extends SpringBootServletInitializer {
    /**
     * 
     * @param builder
     * @return 
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringBootMain.class);
    }
       
    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMain.class, args);
    }
}