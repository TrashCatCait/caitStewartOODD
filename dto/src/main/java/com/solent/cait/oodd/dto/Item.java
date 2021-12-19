/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.solent.cait.oodd.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author caitlyn
 */

@Entity
public class Item {
    private String name = null;
    private String uuid=null;
    private Integer count = 0;
    private Double itemPrice = 0.0;
    private Long id = (long) 0;
    private String[] tags = null;
    
    public Item(){
        
    }

    public Item(String name, Double itemPrice) {
        this.name = name;
        this.itemPrice = itemPrice;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuuid) {
        this.uuid = uuuid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return count;
    }

    public void setQuantity(Integer count) {
        this.count = count;
    }

    public Double getPrice() {
        return itemPrice;
    }

    public void setPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }
    
    public String[] getTags() {
        return tags;
    }
    
    public void setTags(String[] tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "ShoppingItem{" + ", name=" + name + ", quantity=" + count + ", price=" + itemPrice + '}';
    }
    
    
}
