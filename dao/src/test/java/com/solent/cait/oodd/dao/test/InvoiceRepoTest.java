package com.solent.cait.oodd.dao.test;

import com.solent.cait.oodd.dao.InvoiceRepository;
import com.solent.cait.oodd.dto.Item;
import com.solent.cait.oodd.dto.Invoice;
import com.solent.cait.oodd.dto.InvoiceStatus;
import com.solent.cait.oodd.dto.User;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Date;
        
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author caitlyn
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class InvoiceRepoTest {

    private static final Logger LOG = LogManager.getLogger(InvoiceRepoTest.class);

    @Autowired
    private InvoiceRepository invoiceRepo;

    @Test
    public void testInvoiceRepo() {
        LOG.debug("Starting Invoice repo Test");

        Item item1 = new Item();
        item1.setName("TestItem");
        item1.setPrice(100.00);
        item1.setQuantity(1);
        item1.setType("TestType");
        item1.setUuid(UUID.randomUUID().toString());
        
        User user = new User();
        user.setUsername("User1234");
        
        //Clean out the repo
        invoiceRepo.deleteAll();
        List<Item> testItems = new ArrayList();
        testItems.add(item1);
        
        Invoice invoice = new Invoice();
        invoice.setAmountDue(100.00);
        invoice.setDateOfPurchase(new Date());
        invoice.setInvoiceNumber(UUID.randomUUID().toString());
        invoice.setPurchasedItems(testItems);
        invoice.setStatus(InvoiceStatus.OUTSTANDING);
        invoice.setUsername("User1234");
        invoice.setPurchaser(user);
        
        //Check two items are present
        assertEquals(1, invoiceRepo.count());
        
        List<Invoice> invoiceByUUID = invoiceRepo.FindByInoviceNum(invoice.getInvoiceNumber());
        List<Invoice> invoiceByPurchaser = invoiceRepo.FindByUsername(invoice.getUsername());
        Optional<Invoice> invoiceById = invoiceRepo.findById(invoice.getId());
        LOG.debug("Test complete");
    }
}

