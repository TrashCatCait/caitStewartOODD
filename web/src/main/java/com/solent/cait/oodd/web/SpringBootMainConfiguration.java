package com.solent.cait.oodd.web;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import com.solent.cait.oodd.service.ServiceConfiguration;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.solent.cait.oodd.service.ServiceObjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import com.solent.cait.oodd.model.ShoppingService;
import com.solent.cait.oodd.model.UserBasket;
/**
 *
 * @author caitlyn
 */

@Configuration
@PropertySource("classpath:persistence-app.properties")
@Import(ServiceConfiguration.class)
public class SpringBootMainConfiguration {
    @Bean
    ShoppingService getShoppingService() {
        return ServiceObjectFactory.getShoppingService();
    }

    // see https://www.baeldung.com/spring-mvc-session-attributes
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION,
            proxyMode = ScopedProxyMode.TARGET_CLASS)
    public UserBasket getNewBasket() {
        return ServiceObjectFactory.getNewBasket();
    }
}
