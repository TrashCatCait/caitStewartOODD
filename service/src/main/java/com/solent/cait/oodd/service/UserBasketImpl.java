/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solent.cait.oodd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.solent.cait.oodd.model.UserBasket;
import com.solent.cait.oodd.dto.Item;

/**
 *
 * @author caitlyn
 * 
 * Implementation of the User basket interface used to control the user adding items to basket 
 * that are then converted to purchased items at checkout time.
 */
public class UserBasketImpl implements UserBasket {

    private HashMap<String, Item> itemMap = new HashMap<String, Item>();

    /**
     * 
     * @return 
     */
    @Override
    public List<Item> getCurrentBasketItems() {
        List<Item> itemlist = new ArrayList<>();
        for (String itemUUID : itemMap.keySet()) {
            Item item = itemMap.get(itemUUID);
            itemlist.add(item);
        }
        return itemlist;
    }

    /**
     * 
     * @param item 
     */
    @Override
    public void addItemToBasket(Item item) {
        boolean itemExists = false;
        for (String itemUUID : itemMap.keySet()) {
            Item shoppingCartItem = itemMap.get(itemUUID);
            if (shoppingCartItem.getName().equals(item.getName())) {
                Integer q = shoppingCartItem.getQuantity();
                shoppingCartItem.setQuantity(q + 1);
                itemExists = true;
                break;
            }
        }
        if (!itemExists) {
            
            item.setQuantity(1);
            itemMap.put(item.getUuid(), item);
        }
    }

    /**
     * 
     * @param itemUUID 
     */
    @Override
    public void removeItem(String itemUUID) {
        itemMap.remove(itemUUID);
    }

    /**
     * 
     * @return 
     */
    @Override
    public double getTotal() {
        double total = 0;

        for (String itemUUID : itemMap.keySet()) {
            Item shoppingCartItem = itemMap.get(itemUUID);
            total = total + shoppingCartItem.getPrice() * shoppingCartItem.getQuantity();
        }

        return total;

    }

}
