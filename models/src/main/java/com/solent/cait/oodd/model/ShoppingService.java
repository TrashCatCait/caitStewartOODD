/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.solent.cait.oodd.model;

import com.solent.cait.oodd.dto.Item;
import com.solent.cait.oodd.dto.User;
import java.util.List;

/**
 *
 * @author caitlyn
 */
public interface ShoppingService {
    List<Item> getAviliableItems();  
    
    void addItem(Item item);
    
    void purchaseItems(UserBasket basket, User user); 
   
    void removeItemById(Long Id);

    Boolean ItemExistsId(Long id);

}
