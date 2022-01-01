package com.solent.cait.oodd.bank.dto;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.solent.cait.oodd.bank.dto.CreditCard;
import com.solent.cait.oodd.bank.dto.AccountHolder;

@Entity
public class Account {

    private Long id;
    
    private AccountHolder owner = new AccountHolder();

    private String sortcode="";

    private String accountNo="";
    
    private Double balance = 0.0;

    private CreditCard creditcard = new CreditCard();

    private boolean active = true;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Embedded
    public AccountHolder getOwner() {
        return owner;
    }

    public void setOwner(AccountHolder owner) {
        this.owner = owner;
    }

    public String getSortcode() {
        return sortcode;
    }

    public void setSortcode(String sortcode) {
        this.sortcode = sortcode;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    @Embedded
    public CreditCard getCreditcard() {
        return creditcard;
    }

    public void setCreditcard(CreditCard creditcard) {
        this.creditcard = creditcard;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BankAccount{" + "id=" + id + ", owner=" + owner + ", sortcode=" + sortcode + ", accountNo=" + accountNo + ", balance=" + balance + ", creditcard=" + creditcard + ", active=" + active + '}';
    }


    
    
    
}
