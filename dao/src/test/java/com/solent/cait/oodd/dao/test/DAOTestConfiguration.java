/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solent.cait.oodd.dao.test;

import com.solent.cait.oodd.dao.JPAPersistenceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;


/**
 *
 * @author caitlyn 
 * Sets up the DAO configuration for the test modules to use
 */
@Configuration
@Import(JPAPersistenceConfig.class)
@PropertySource("classpath:persistence-test.properties")
public class DAOTestConfiguration {
    
}
