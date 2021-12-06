/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.solent.cait.oodd.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.solent.cait.oodd.dao.JPAPersistenceConfig;

/**
 *
 * @author caitlyn
 */
@Configuration

@ComponentScan(basePackages = {"com.solent.cait.oodd.spring",
    "com.solent.cait.oodd.service"
})
@Import({JPAPersistenceConfig.class})
public class ServiceConfiguration {

}
