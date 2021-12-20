/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solent.cait.oodd.service.test;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.solent.cait.oodd.dto.Item;
import com.solent.cait.oodd.model.UserBasket;
import com.solent.cait.oodd.service.ServiceObjectFactory;
import java.util.UUID;

/**
 *
 * @author cgallen
 */
public class UserBasketTest {

    UserBasket basket = null;

    @Before
    public void before() {
        basket = ServiceObjectFactory.getNewBasket();
        basket.getCurrentBasketItems().clear();
    }

    @Test
    public void testBasketNotNull() {
        assertNotNull(basket);
    }

    @Test
    public void testUserBasket() {
        assertNotNull(basket);
        
        List<Item> items = basket.getCurrentBasketItems();
        
        assertTrue(items.isEmpty());
        
        Item newItem = new Item();
        newItem.setName("TestItem");
        newItem.setPrice(100.00);
        newItem.setQuantity(1);
        newItem.setType("ItemType");
        newItem.setUuid(UUID.randomUUID().toString());
        
        basket.addItemToBasket(newItem);
        
        assertEquals(1, basket.getCurrentBasketItems().size());
        
        basket.removeItem(newItem.getUuid());
        
        assertEquals(0, basket.getCurrentBasketItems().size());

    }

    // add your own tests here
}
