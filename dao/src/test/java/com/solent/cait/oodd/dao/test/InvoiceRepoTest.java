package com.solent.cait.oodd.dao.test;

import com.solent.cait.oodd.dao.InvoiceRepository;
import com.solent.cait.oodd.dao.UserRepository;
import com.solent.cait.oodd.dao.ItemCatalogRepository;
import com.solent.cait.oodd.dto.Item;
import com.solent.cait.oodd.dto.Invoice;
import com.solent.cait.oodd.dto.InvoiceStatus;
import com.solent.cait.oodd.dto.User;
import java.util.List;
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
import com.solent.cait.oodd.dto.PurchasedItem;
import javax.transaction.Transactional;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 *
 * @author caitlyn 
 * 
 * Invoice repo test module goes through and tests the the features of the Invoice repository to ensure it works as it's supposed to 
 * with no unexpected errors
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DAOTestConfiguration.class, loader = AnnotationConfigContextLoader.class)
@Transactional
public class InvoiceRepoTest {

    private static final Logger LOG = LogManager.getLogger(InvoiceRepoTest.class);

    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private InvoiceRepository invoiceRepo;
    
    @Autowired 
    private ItemCatalogRepository itemRepo;

    @Test
    public void testInvoiceRepo() {
        LOG.debug("Starting Invoice repo Test");

        Item item1 = new Item();
        item1.setName("TestItem");
        item1.setPrice(100.00);
        item1.setQuantity(1);
        item1.setType("TestType");
        item1.setUuid(UUID.randomUUID().toString());
        item1 = itemRepo.save(item1);
        
        User user = new User();
        user.setUsername("User1234");  
        user = userRepo.save(user);
        
        //Clean out the repo
        invoiceRepo.deleteAll();
        List<PurchasedItem> testItems = new ArrayList();
        PurchasedItem pitem = new PurchasedItem();
        pitem.setItem(item1);
        pitem.setCount(11);
        testItems.add(pitem);

        Invoice invoice = new Invoice();
        invoice.setAmountDue(100.00);
        invoice.setDateOfPurchase(new Date());
        invoice.setInvoiceNumber(UUID.randomUUID().toString());
        invoice.setPurchasedItems(testItems);
        invoice.setStatus(InvoiceStatus.OUTSTANDING);
        invoice.setUsername("User1234");
        invoice.setPurchaser(user);
        invoice = invoiceRepo.save(invoice);
        assertEquals(1, invoiceRepo.count());

        invoiceRepo.deleteById(invoice.getId());
        assertEquals(0, invoiceRepo.count());
        LOG.debug("Test complete");
    }
}
