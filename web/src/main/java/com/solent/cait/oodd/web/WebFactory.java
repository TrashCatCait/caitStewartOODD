/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solent.cait.oodd.web;


import com.solent.cait.oodd.model.ShoppingService;
import com.solent.cait.oodd.model.UserBasket;
import com.solent.cait.oodd.service.ServiceObjectFactory;

/**
 *
 * @author caitlyn
 * Web Factory calls service factory in order to get objects to be created for the web application such as the basket and 
 * shopping service of the web application.
 */
public class WebFactory {

    static ShoppingService shoppingService = ServiceObjectFactory.getShoppingService();
    
    // cannot instantiate
    private WebFactory(){
        
    }
    
    /**
     * 
     * @return 
     */
    public static ShoppingService getShoppingService(){
        return shoppingService;
    }
    
    /**
     * 
     * @return 
     */
    public static UserBasket getNewBasket(){
        return ServiceObjectFactory.getNewBasket();
    }
    
}
