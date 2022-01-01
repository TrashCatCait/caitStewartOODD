/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solent.cait.oodd.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import com.solent.cait.oodd.bank.model.BankClient;
import com.solent.cait.oodd.bank.service.BankClientImpl;
import com.solent.cait.oodd.bank.dto.TransactionStatus;
import com.solent.cait.oodd.bank.dto.CreditCard;
import com.solent.cait.oodd.bank.dto.TransactionReply;

/**
 *
 * @author cgallen
 */
public class BankClientTest {

    final static Logger LOG = LogManager.getLogger(BankClientTest.class);

    String bankUrl = "http://localhost:8080/bank/rest";
    CreditCard fromCard = null;
    CreditCard toCard = null;
    
    String toUsername=null;
    String toPassword=null;

    @Before
    public void before() {
        fromCard = new CreditCard();
        fromCard.setCardnumber("5133880000000012");
        fromCard.setCvv("123");
        fromCard.setEndDate("11/21");
        fromCard.setIssueNumber("01"); 
        fromCard.setName("test user1");

        toCard = new CreditCard();
        toCard.setCardnumber("4285860000000021");
        toCard.setCvv("123");
        toCard.setEndDate("11/21");
        toCard.setIssueNumber("01");
        toCard.setName("test user2");
        
        toUsername = "testuser2";
        toPassword = "defaulttestpass";
    }

    @Test
    public void testClient() {

        BankClient client = new BankClientImpl(bankUrl);

        Double amount = 0.0;
        //Error cause bank keeps 404 ing unsure why

        //TransactionReply reply = client.transferMoney(fromCard, toCard, amount);
        //LOG.debug("transaction reply:" + reply);

        //assertEquals(TransactionStatus.SUCCESS, reply.getStatus());

    }

    @Test
    public void testClientAuth() {

        BankClient client = new BankClientImpl(bankUrl);

        Double amount = 0.0;
        //Error cause bank keeps 404 ing unsure why

        // testing with auth
 
        //TransactionReply reply = client.transferMoney(fromCard, toCard, amount, toUsername, toPassword);
        //LOG.debug("transaction with auth reply:" + reply);
        
        //assertEquals(TransactionStatus.SUCCESS, reply.getStatus());

    }
}
