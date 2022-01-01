/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solent.cait.oodd.bank.service;

import java.util.logging.Level;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.logging.LoggingFeature;

import com.solent.cait.oodd.bank.dto.TransactionReply;
import com.solent.cait.oodd.bank.dto.TransactionRequest;
import com.solent.cait.oodd.bank.dto.CreditCard;
import com.solent.cait.oodd.bank.model.BankClient;
import javax.inject.Scope;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 *
 * @author caitlyn
 * Implemetation of the bank client interface it's should handle money being sent from cards
 * However it does not current work and always returns connection refused (I'm unsure why this is happening)
 * 
 */
public class BankClientImpl implements BankClient {

    final static Logger LOG = LogManager.getLogger(BankClientImpl.class);

    String urlStr;

    /**
     * 
     * @param urlStr 
     */
    public BankClientImpl(String urlStr) {
        this.urlStr = urlStr;
    }

    /**
     * 
     * @param fromCard
     * @param toCard
     * @param amount
     * @return 
     */
    @Override
    public TransactionReply transferMoney(CreditCard fromCard, CreditCard toCard, Double amount) {
        LOG.debug("transferMoney called: ");
        
        // sets up logging for the client       
        Client client = ClientBuilder.newClient(new ClientConfig().register(
                new LoggingFeature(java.util.logging.Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME),
                        Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY, 10000)));

        // allows client to decode json
        client.register(JacksonJsonProvider.class);

        WebTarget webTarget = client.target(urlStr).path("/transactionRequest");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(amount);
        transactionRequest.setFromCard(fromCard);
        transactionRequest.setToCard(toCard);

        Response response = invocationBuilder.post(Entity.entity(transactionRequest, MediaType.APPLICATION_JSON));

        TransactionReply transactionReply = response.readEntity(TransactionReply.class);

        LOG.debug("Response status=" + response.getStatus() + " ReplyMessage: " + transactionReply);

        return transactionReply;

    }

    /**
     * 
     * @param fromCard
     * @param toCard
     * @param amount
     * @param userName
     * @param password
     * @return 
     */
    @Override
    public TransactionReply transferMoney(CreditCard fromCard, CreditCard toCard, Double amount, String userName, String password) {
        LOG.debug("transferMoney called: ");

        // sets up logging for the client       
        Client client = ClientBuilder.newClient(new ClientConfig().register(
                new LoggingFeature(java.util.logging.Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME),
                        Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY, 10000)));

        // basic authentication
        HttpAuthenticationFeature basicAuthfeature = HttpAuthenticationFeature.basic(userName, password);
        client.register(basicAuthfeature);
        
        
        // allows client to decode json
        client.register(JacksonJsonProvider.class);
        WebTarget webTarget = client.target(urlStr).path("/transactionRequest");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(amount);
        transactionRequest.setFromCard(fromCard);
        transactionRequest.setToCard(toCard);

        Response response = invocationBuilder.post(Entity.entity(transactionRequest, MediaType.APPLICATION_JSON));

        TransactionReply transactionReplyMessage = response.readEntity(TransactionReply.class);

        LOG.debug("Response status=" + response.getStatus() + " ReplyMessage: " + transactionReplyMessage);

        return transactionReplyMessage;

    }
}
