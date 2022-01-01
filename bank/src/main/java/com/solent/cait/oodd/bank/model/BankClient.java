package com.solent.cait.oodd.bank.model;

import com.solent.cait.oodd.bank.dto.CreditCard;
import com.solent.cait.oodd.bank.dto.TransactionReply;

public interface BankClient {

    public  TransactionReply transferMoney(CreditCard fromCard, CreditCard toCard, Double amount);
    public  TransactionReply transferMoney(CreditCard fromCard, CreditCard toCard, Double amount, String userName, String password);
}
