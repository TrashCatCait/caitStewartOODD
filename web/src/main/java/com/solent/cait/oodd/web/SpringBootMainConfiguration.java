package com.solent.cait.oodd.web;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import com.solent.cait.oodd.service.ServiceConfiguration;
import com.solent.cait.oodd.service.ServiceObjectFactory;
import com.solent.cait.oodd.model.ShoppingService;
import com.solent.cait.oodd.model.UserBasket;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;



/**
 *
 * @author caitlyn
 * Creates a configuration for the DAO beans used in the web application. 
 * Allowing them to be Autowired and for the DAOs to be easyly setup for the app.
 */

@Configuration
@Import(ServiceConfiguration.class)
@PropertySource("classpath:persistence-app.properties")
public class SpringBootMainConfiguration {
    /**
     * 
     * @return 
     */
    @Bean
    ShoppingService getShoppingService() {
        return ServiceObjectFactory.getShoppingService();
    }

    /**
     * 
     * @return 
     */
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION,
            proxyMode = ScopedProxyMode.TARGET_CLASS)
    public UserBasket getNewBasket() {
        return ServiceObjectFactory.getNewBasket();
    }

}
