/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.solent.cait.oodd.dto;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


/**
 *
 * @author caitlyn
 *
 * Item that is created at checkout this was to avoid a bug related to the quantities of orders being displayed
 * incorrectly due to the JPA lib always importing item as Foreign Key rather than a field in the invoice database
 * So this is a little bit of a hack/workaround and I would've preferred to get the SQL to work right but this also works
 * I also tried adding a quantity list directly to Invoice but that didn't work and cause more issues than it solved
 * 
 */
@Entity
public class PurchasedItem {
    
    private Long id = (long) 0;
    private String name = null;
    private String uuid=null;
    private Integer count = 0;
    private Double itemPrice = 0.0;
    private String type = null;    
    
     /**
     * 
     * @return 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return 
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 
     * @param uuid 
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 
     * @return 
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 
     * @return 
     */
    public Integer getQuantity() {
        return count;
    }

    /**
     * 
     * @param count 
     */
    public void setQuantity(Integer count) {
        this.count = count;
    }

    /**
     * 
     * @return 
     */
    public Double getPrice() {
        return itemPrice;
    }

    /**
     * 
     * @param itemPrice 
     */
    public void setPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }
    
    /**
     * 
     * @return 
     */
    public String getType() {
        return type;
    }
    
    /**
     * 
     * @param type 
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "ShoppingItem{" + ", name=" + name + ", quantity=" + count + ", price=" + itemPrice + '}';
    }
}
