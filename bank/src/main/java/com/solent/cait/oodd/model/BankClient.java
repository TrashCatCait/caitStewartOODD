package com.solent.cait.oodd.model;

import com.solent.cait.oodd.dto.CreditCard;
import com.solent.cait.oodd.dto.TransactionReply;

public interface BankClient {

    public  TransactionReply transferMoney(CreditCard fromCard, CreditCard toCard, Double amount);
    public  TransactionReply transferMoney(CreditCard fromCard, CreditCard toCard, Double amount, String userName, String password);
}
