/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.solent.cait.oodd.dao.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import com.solent.cait.oodd.dao.ItemCatalogRepository;
import com.solent.cait.oodd.dao.ItemCatalogRepository;
import com.solent.cait.oodd.dto.Item;
import java.util.List;

/**
 *
 * @author caitlyn
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ItemCatalogRepoTest {

    private static final Logger LOG = LogManager.getLogger(ItemCatalogRepoTest.class);

    @Autowired
    private ItemCatalogRepository itemRepo;

    @Test
    public void testItemRepo() {
        LOG.debug("Starting item repo Test");
        
        //Clean out the repo
        itemRepo.deleteAll();
        
        Item itemToDel = new Item();
        itemToDel.setName("ItemToDel");
        itemToDel.setPrice(100.00);
        itemToDel.setQuantity(50);
        itemToDel.setType("Soon To Be Removed");
        itemToDel.setUuid(UUID.randomUUID().toString());
        
        Item itemToSave = new Item();
        itemToSave.setName("Item1");
        itemToSave.setPrice(100.00);
        itemToSave.setQuantity(50);
        itemToSave.setType("Type");
        itemToSave.setUuid(UUID.randomUUID().toString());
        
        itemToDel = itemRepo.save(itemToDel);
        itemToSave = itemRepo.save(itemToSave);
        
        //Check two items are present
        assertEquals(2, itemRepo.count());
        
        //Delete Item
        itemRepo.deleteById(itemToDel.getId());
                
        //Check only one exist now
        assertEquals(1, itemRepo.count());
        
        Optional<Item> optionalItem = itemRepo.findById(itemToSave.getId()); 
        List<Item> itemsByType = itemRepo.FindByType("Type");
        List<Item> itemsByName = itemRepo.FindByName("Item1");
        
        LOG.debug("Found item: " + optionalItem.get());
        LOG.debug("Found Item By Name " + itemsByName);
        LOG.debug("Found by Type: " + itemsByType);
        LOG.debug("Test complete");
    }
}
