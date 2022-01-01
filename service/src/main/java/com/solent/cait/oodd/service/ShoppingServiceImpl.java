/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solent.cait.oodd.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import com.solent.cait.oodd.model.ShoppingService;
import com.solent.cait.oodd.dto.Item;
import com.solent.cait.oodd.dao.ItemCatalogRepository;
import com.solent.cait.oodd.dao.InvoiceRepository;
import com.solent.cait.oodd.dto.Invoice;
import com.solent.cait.oodd.dto.InvoiceStatus;
import com.solent.cait.oodd.dto.User;
import com.solent.cait.oodd.model.UserBasket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import com.solent.cait.oodd.bank.dto.CreditCard;
import com.solent.cait.oodd.bank.dto.TransactionReply;
import com.solent.cait.oodd.bank.model.BankClient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 *
 * @author cgallen
 */
public class ShoppingServiceImpl implements ShoppingService {

    final static Logger LOG = LogManager.getLogger(ShoppingServiceImpl.class);

    @Autowired
    private InvoiceRepository invoiceRepo;

    @Autowired
    private ItemCatalogRepository itemRepo;
    
    
    private CreditCard shoppingServiceCard;
    
    private BankClient client = ServiceObjectFactory.getBankClient();
    
    public ShoppingServiceImpl() {
        //Create the companies card Generated by a fake card generator. So should actually be anyones details
        shoppingServiceCard = new CreditCard();
        
        shoppingServiceCard.setCardnumber("4257022822028847");
        shoppingServiceCard.setCvv("880");
        shoppingServiceCard.setEndDate("03/22");
        shoppingServiceCard.setIssueNumber("01");
        shoppingServiceCard.setName("Bob Roggers");
        
    }
    
    //Returns True if succeeded.
    @Override
    public Boolean purchaseItems(List<Item> items, Double total, User user, CreditCard purchaseCard) {
        Invoice newInvoice = new Invoice();
        //I don't know why the items are display the wrong quantity on order screen because they work out the basket
        //Quantity okay it's very weird.
        LOG.info(items);
        LOG.info(purchaseCard);
        LOG.info(total);
        newInvoice.setAmountDue(total);
        newInvoice.setPurchaser(user);
        newInvoice.setInvoiceNumber(UUID.randomUUID().toString());
        newInvoice.setDateOfPurchase(new Date());
        newInvoice.setStatus(InvoiceStatus.OUTSTANDING);
        newInvoice.setUsername(user.getUsername());
        client = ServiceObjectFactory.getBankClient();
        LOG.info(shoppingServiceCard);
        LOG.info(client);
        
        //Attempt to work with the bank always produces 404 and javax.ws.rs.ProcessingException: Error reading entity from input stream. 
        //Exception error commented out for the sake of making it work
        //TransactionReply transaction = client.transferMoney(purchaseCard, shoppingServiceCard, total);
        //if(transaction.getStatus().toString().toUpperCase().equals("SUCCESS")) {
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            //Get the database version of this item 
            Optional<Item> repoItem = itemRepo.findById(item.getId());

            //update stock items 
            if (repoItem.isEmpty()) {
                return false;

            } else {
                if (repoItem.get().getQuantity() < item.getQuantity()) {
                    return false;
                }
                repoItem.get().setQuantity(repoItem.get().getQuantity() - item.getQuantity());
                itemRepo.save(repoItem.get());
            }
        }
        newInvoice.setPurchasedItems(items);
        invoiceRepo.save(newInvoice);
        
        return true;
    }
    //    return false;
    //}


    /*
    * Deletes Item from the repo using the Item's ID to do so
    */
    @Override
    public void removeItemById(Long Id) {
        itemRepo.deleteById(Id); 
    }
    
    @Override
    public Boolean ItemExistsId(Long id) {
        return itemRepo.existsById(id);
    }

/*
    Returns all the items either named String or with type of string
*/
    @Override
    public List<Item> getItemsByString(String searchStr) {
        List<Item> typeSearchItems = itemRepo.FindByType(searchStr);
        List<Item> nameSearchItems = itemRepo.FindByName(searchStr);
        
        List<Item> newList = Stream.of(typeSearchItems, nameSearchItems).flatMap(Collection::stream).collect(Collectors.toList());
        return newList;
    }

    @Override
    public Item getItemById(Long id) {
        Optional<Item> item = itemRepo.findById(id);
        if(item.isPresent()){
            itemRepo.save(item.get());
            return item.get();
        }
        return null;
    }
    
    @Override
    public void addItem(Item item) {
        itemRepo.save(item);
    }

    @Override
    public List<Item> getAviliableItems() {
        return itemRepo.findAll();
    }

}
