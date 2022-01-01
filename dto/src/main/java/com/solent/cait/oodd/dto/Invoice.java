/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.solent.cait.oodd.dto;



import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author caitlyn
 */
@Entity
public class Invoice {

    private Long id;
    
    private String invoiceNumber;

    private Date dateOfPurchase;

    private Double amountDue;
    
    private List<PurchasedItem> purchasedItems;
    
    private User purchaser;
    
    private String username;
    
    private InvoiceStatus status;

    /**
     * 
     */
    public Invoice(){
        
    }
    
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
    @Column(name="username")
    public String getUsername() {
        return username;
    }
    
    /**
     * 
     * @param username 
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * 
     * @return 
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * 
     * @param invoiceNumber 
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * 
     * @param status 
     */
    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }
  
    /**
     * 
     * @return 
     */
    public InvoiceStatus getStatus() {
        return status;
    }

    /**
     * 
     * @return 
     */
    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    /**
     * 
     * @param dateOfPurchase 
     */
    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    /**
     * 
     * @return 
     */
    public Double getAmountDue() {
        return amountDue;
    }

    /**
     * 
     * @param amountDue 
     */
    public void setAmountDue(Double amountDue) {
        this.amountDue = amountDue;
    }
    
    /*
    * Cascade type persit used as the program runs into and exception without it.
    * seeming to be releasted to the fact that the item entity realted to purchasedItem
    * Isn't persitant otherwise.
    */
    
    /**
     * 
     * @return 
     */
    @OneToMany(cascade = CascadeType.PERSIST)
    public List<PurchasedItem> getPurchasedItems() {
        return purchasedItems;
    }

    /**
     * 
     * @param purchasedItems 
     */
    public void setPurchasedItems(List<PurchasedItem> purchasedItems) {
        this.purchasedItems = purchasedItems;
    }

    /**
     * 
     * @return 
     */
    @OneToOne
    public User getPurchaser() {
        return purchaser;
    }

    /**
     * 
     * @param purchaser 
     */
    public void setPurchaser(User purchaser) {
        this.purchaser = purchaser;
    }

}
