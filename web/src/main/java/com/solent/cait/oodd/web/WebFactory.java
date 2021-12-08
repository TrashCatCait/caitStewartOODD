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
 * @author cgallen
 */
public class WebFactory {

    static ShoppingService shoppingService = ServiceObjectFactory.getShoppingService();
    
    // cannot instantiate
    private WebFactory(){
        
    }
    
    public static ShoppingService getShoppingService(){
        return shoppingService;
    }
    
    public static UserBasket getNewBasket(){
        return ServiceObjectFactory.getNewBasket();
    }
    
}
