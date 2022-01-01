/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solent.cait.oodd.service;

import com.solent.cait.oodd.model.ShoppingService;
import com.solent.cait.oodd.model.UserBasket;
import com.solent.cait.oodd.bank.model.BankClient;
import com.solent.cait.oodd.bank.service.BankClientImpl;


/**
 *
 * @author cgallen
 */
public class ServiceObjectFactory {
    
    static ShoppingService shoppingService = new ShoppingServiceImpl();
    
    static BankClient bankClient = new BankClientImpl("http://localhost:8080/rest");
    
    // cannot instantiate
    private ServiceObjectFactory(){
        
    }
    
    public static BankClient getBankClient() {
        return bankClient;
    }
    
    public static ShoppingService getShoppingService(){
        return shoppingService;
    }
    
    public static UserBasket getNewBasket(){
        return new UserBasketImpl();
    }
    
}
