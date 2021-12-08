/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solent.cait.oodd.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import com.solent.cait.oodd.model.ShoppingService;
import com.solent.cait.oodd.dto.Item;
import com.solent.cait.oodd.model.UserBasket;

/**
 *
 * @author cgallen
 */

public class ShoppingServiceImpl implements ShoppingService {

    // note ConcurrentHashMap instead of HashMap if map can be altered while being read
    private Map<String, Item> itemMap = new ConcurrentHashMap<String, Item>();

    private List<Item> itemlist = Arrays.asList(new Item("house", 20000.00),
            new Item("hen", 5.00),
            new Item("car", 5000.00),
            new Item("pet alligator", 65.00)
    );
    
    public UserBasket purchaseItems(UserBasket basket) {
        return null;
        
    }
    
    public ShoppingServiceImpl() {

        // initialised the hashmap
        for (Item item : itemlist) {
            itemMap.put(item.getName(), item);
        }
    }

    @Override
    public List<Item> getCurrentBasketItems() {
        return itemlist;
    }

    @Override
    public Item getItemByName(String name) {
        Item templateItem = itemMap.get(name);
        
        if(templateItem==null) return null;
        
        Item item = new Item();
        item.setName(name);
        item.setPrice(templateItem.getPrice());
        item.setQuantity(0);
        item.setUuid(UUID.randomUUID().toString());
        return item;
    }
    
    @Override
    public Item getItemByUUID(String UUID) {
        return null;
    }

}
