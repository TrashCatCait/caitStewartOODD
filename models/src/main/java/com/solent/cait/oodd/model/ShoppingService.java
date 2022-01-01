/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.solent.cait.oodd.model;

import com.solent.cait.oodd.dto.Item;
import com.solent.cait.oodd.dto.User;
import java.util.List;
import com.solent.cait.oodd.bank.dto.CreditCard;


/**
 *
 * @author caitlyn
 */
public interface ShoppingService {
    
    /**
     * 
     * @return 
     */
    List<Item> getAviliableItems();

    /**
     * 
     * @param item 
     */
    void addItem(Item item);

    /**
     * 
     * @param items
     * @param total
     * @param user
     * @param purchaseCard
     * @return 
     */
    Boolean purchaseItems(List<Item> items, Double total, User user, CreditCard purchaseCard);

    /**
     * 
     * @param Id 
     */
    void removeItemById(Long Id);

    /**
     * 
     * @param id
     * @return 
     */
    Boolean ItemExistsId(Long id);

    /**
     * 
     * @param id
     * @return 
     */
    Item getItemById(Long id);

    /**
     * 
     * @param searchStr
     * @return 
     */
    List<Item> getItemsByString(String searchStr);
}
