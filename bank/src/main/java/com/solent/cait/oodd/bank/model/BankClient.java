package com.solent.cait.oodd.bank.model;

import com.solent.cait.oodd.bank.dto.CreditCard;
import com.solent.cait.oodd.bank.dto.TransactionReply;

/**
 * 
 * @author caitlyn
 * Bank client model
 */
public interface BankClient {

    /**
     * 
     * @param fromCard
     * @param toCard
     * @param amount
     * @return 
     */
    public  TransactionReply transferMoney(CreditCard fromCard, CreditCard toCard, Double amount);
    
    /**
     * 
     * @param fromCard
     * @param toCard
     * @param amount
     * @param userName
     * @param password
     * @return 
     */
    public  TransactionReply transferMoney(CreditCard fromCard, CreditCard toCard, Double amount, String userName, String password);
}
