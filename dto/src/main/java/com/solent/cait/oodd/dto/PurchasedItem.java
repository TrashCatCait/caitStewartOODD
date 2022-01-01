/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.solent.cait.oodd.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


/**
 *
 * @author caitlyn
 * Item that is created at checkout this was to avoid a bug related to the quantities of orders being displayed
 * incorrectly due to the JPA lib always importing item as Foreign Key rather than a field in the invoice database
 * So like this is a little bit of a hack and I would've preferred to get the SQL to work right but this also works
 * I also tried adding a quantity list directly to Invoice but that didn't work and cause more issues than it solved
 */
@Entity
public class PurchasedItem {
    
    private Long id = (long) 0;
    private Item item;
    private int count;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
    @OneToOne
    public Item getItem() {
        return item;
    }
    
    public void setItem(Item item) {
        this.item = item;
    }
}
