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
 * @author cgallen
 */
public class UserBasketImpl implements UserBasket {

    private HashMap<String, Item> itemMap = new HashMap<String, Item>();

    @Override
    public List<Item> getCurrentBasketItems() {
        List<Item> itemlist = new ArrayList();
        for (String itemUUID : itemMap.keySet()) {
            Item shoppingCartItem = itemMap.get(itemUUID);
            itemlist.add(shoppingCartItem);
        }
        return itemlist;
    }

    @Override
    public void addItemToBasket(Item item) {
        // itemMap.put(shoppingItem.getUuid(), shoppingItem);
        
        // ANSWER
        boolean itemExists = false;
        for (String itemUUID : itemMap.keySet()) {
            Item shoppingCartItem = itemMap.get(itemUUID);
            if (shoppingCartItem.getName().equals(item.getName())){
                Integer q = shoppingCartItem.getQuantity();
                shoppingCartItem.setQuantity(q+1);
                itemExists = true;
                break;
            }
        }
        if (!itemExists){
            item.setQuantity(1);
            itemMap.put(item.getUuid(), item);
        }
    }

    @Override
    public void removeItem(String itemUuid) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        // ANSWER
        itemMap.remove(itemUuid);
    }

    @Override
    public double getTotal() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        // ANSWER
        double total = 0;

        for (String itemUUID : itemMap.keySet()) {
            Item shoppingCartItem = itemMap.get(itemUUID);
            total = total + shoppingCartItem.getPrice() * shoppingCartItem.getQuantity();
        }

        return total;

    }

}
