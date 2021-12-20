/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solent.cait.oodd.service.test;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.solent.cait.oodd.model.ShoppingService;
import com.solent.cait.oodd.service.ServiceObjectFactory;

/**
 *
 * @author cgallen
 */
public class ShoppingServiceTest {
    
    ShoppingService shoppingService = null;

    @Before
    public void before(){
        shoppingService = ServiceObjectFactory.getShoppingService();
        
    }
    
    @Test
    public void serviceNotNull() {
        assertNotNull(shoppingService);
    }
}
