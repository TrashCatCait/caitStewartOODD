package com.solent.cait.oodd.bank.dto;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * @author caitlyn
 * Entity object of Users bank account
 *
 */
@Entity
public class Account {

    private Long id;
    
    private AccountHolder owner = new AccountHolder();

    private String sortcode="";

    private String accountNo="";
    
    private Double balance = 0.0;

    private CreditCard creditcard = new CreditCard();

    private boolean active = true;

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
    @Embedded
    public AccountHolder getOwner() {
        return owner;
    }

    /**
     * 
     * @param owner 
     */
    public void setOwner(AccountHolder owner) {
        this.owner = owner;
    }

    /**
     * 
     * @return 
     */
    public String getSortcode() {
        return sortcode;
    }

    /**
     * 
     * @param sortcode 
     */
    public void setSortcode(String sortcode) {
        this.sortcode = sortcode;
    }

    /**
     * 
     * @return 
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * 
     * @param accountNo 
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    /**
     * 
     * @return 
     */
    @Embedded
    public CreditCard getCreditcard() {
        return creditcard;
    }

    /**
     * 
     * @param creditcard 
     */
    public void setCreditcard(CreditCard creditcard) {
        this.creditcard = creditcard;
    }

    /**
     * 
     * @return 
     */
    public boolean isActive() {
        return active;
    }

    /**
     * 
     * @param active 
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * 
     * @return 
     */
    public Double getBalance() {
        return balance;
    }

    /**
     * 
     * @param balance 
     */
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "BankAccount{" + "id=" + id + ", owner=" + owner + ", sortcode=" + sortcode + ", accountNo=" + accountNo + ", balance=" + balance + ", creditcard=" + creditcard + ", active=" + active + '}';
    }
}
