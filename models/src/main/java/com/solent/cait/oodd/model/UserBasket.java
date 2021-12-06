/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.solent.cait.oodd.model;

import com.solent.cait.oodd.dto.Item;
import java.util.List;

/**
 *
 * @author caitlyn
 */
public interface UserBasket {
    List<Item> getCurrentBasketItems();
    
    Item addItemToChar(Item item);
    
    String removeItem(String itemUUID);
}
