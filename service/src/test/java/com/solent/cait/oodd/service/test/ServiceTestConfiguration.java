/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solent.cait.oodd.service.test;


import com.solent.cait.oodd.service.ServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;


/**
 *
 * @author caitlyn
 */
@Configuration
@Import(ServiceConfiguration.class)
@PropertySource("classpath:service-test.properties")
public class ServiceTestConfiguration {
    
}
