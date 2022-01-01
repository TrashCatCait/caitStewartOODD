package com.solent.cait.oodd.service.test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author caitlyn
 * Basic Test class to make sure credit card Checker returns expected outputs;
 */
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.solent.cait.oodd.model.ShoppingService;
import com.solent.cait.oodd.service.ServiceObjectFactory;
import com.solent.cait.oodd.dto.User;
import com.solent.cait.oodd.bank.dto.CreditCard;
import com.solent.cait.oodd.service.CardChecker;


public class CardCheckerTest {

    private CreditCard shoppingServiceCard;
    
    private CardChecker checker = new CardChecker();
    
    @Before
    public void before(){
        shoppingServiceCard = new CreditCard();
                
        shoppingServiceCard.setCardnumber("4257022822028847");
        shoppingServiceCard.setCvv("880");
        shoppingServiceCard.setEndDate("03/22");
        shoppingServiceCard.setIssueNumber("01");
        shoppingServiceCard.setName("Bob Roggers");
        
    }
    
    @Test
    public void cardNotNull() {
        assertNotNull(shoppingServiceCard);
        assertNotNull(checker);
    }
    
    @Test 
    public void checkCardNo() {
        assertTrue(checker.isValidCard(shoppingServiceCard.getCardnumber()));
        assertFalse(checker.isValidCard("0000000000000000"));
    }
    
    @Test
    public void checkCVV() {
        assertTrue(checker.checkCVV(shoppingServiceCard.getCvv()));
        assertFalse(checker.checkCVV("000000"));
    }
    
    @Test 
    public void checkExpire() {
        assertTrue(checker.checkExpire(shoppingServiceCard.getEndDate()));
        assertFalse(checker.checkExpire("1800214589"));
    }
    
}
