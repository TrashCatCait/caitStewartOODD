package com.solent.cait.oodd.bank.dto;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * 
 * @author caitlyn
 * Transactions object layout
 */
@Entity
public class Transaction {

    private Long id;
    
    private String transactionId = UUID.randomUUID().toString();

    private Account fromAccount;

    private Account toAccount;

    private Date transactionDate = new Date();

    private Double amount;

    private TransactionStatus status;

    private String message;

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
    @OneToOne
    public Account getFromAccount() {
        return fromAccount;
    }

    /**
     * 
     * @param fromAccount 
     */
    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    /**
     * 
     * @return 
     */
    @OneToOne
    public Account getToAccount() {
        return toAccount;
    }

    /**
     * 
     * @param toAccount 
     */
    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    /**
     * 
     * @return 
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * 
     * @param transactionDate 
     */
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * 
     * @return 
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * 
     * @param amount 
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * 
     * @return 
     */
    public TransactionStatus getStatus() {
        return status;
    }

    /**
     * 
     * @param status 
     */
    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    /**
     * 
     * @return 
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message 
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * @return 
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * 
     * @param transactionId 
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "BankTransaction{" + "id=" + id + ", transactionId=" + transactionId + ", fromAccount=" + fromAccount + ", toAccount=" + toAccount + ", transactionDate=" + transactionDate + ", amount=" + amount + ", status=" + status + ", message=" + message + '}';
    }


}
