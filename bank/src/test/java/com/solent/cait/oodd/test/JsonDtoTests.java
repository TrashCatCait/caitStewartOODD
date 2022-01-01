/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solent.cait.oodd.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import com.solent.cait.oodd.bank.dto.CreditCard;
import com.solent.cait.oodd.bank.dto.TransactionRequest;

/**
 *
 * @author cgallen
 */
public class JsonDtoTests {

    private static final Logger LOG = LogManager.getLogger(JsonDtoTests.class);

    ObjectMapper objectMapper = null;

    @Before
    public void before() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.findAndRegisterModules()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    public void testJsonMessages() throws JsonProcessingException {
        TransactionRequest trequest = new TransactionRequest();
        CreditCard fromCard = new CreditCard();
        trequest.setFromCard(fromCard);
        CreditCard toCard = new CreditCard();
        trequest.setToCard(toCard);

        Double amount = 100.01;

        trequest.setAmount(amount);

        String tRequestString = objectMapper.writeValueAsString(trequest);

        LOG.debug("Json transactionRequest output:\n" + tRequestString);

        TransactionRequest receivedTransactionRequest = objectMapper.readValue(tRequestString, TransactionRequest.class);
        
        assertTrue(trequest.toString().equals(receivedTransactionRequest.toString()));
    }
}
