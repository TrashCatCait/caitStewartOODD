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
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author cgallen
 */
public class ShoppingServiceImpl implements ShoppingService {

    @Autowired
    private InvoiceRepository invoiceRepo;

    @Autowired
    private ItemCatalogRepository itemRepo;

    //Returns True if succeeded.
    @Override
    public Boolean purchaseItems(List<Item> items, Double total, User user) {
        Invoice newInvoice = new Invoice();
        //I don't know why the items are display the wrong quantity on order screen because they work out the basket
        //Quantity okay it's very weird.
        newInvoice.setPurchasedItems(items);
        newInvoice.setAmountDue(total);
        newInvoice.setPurchaser(user);
        newInvoice.setInvoiceNumber(UUID.randomUUID().toString());
        newInvoice.setDateOfPurchase(new Date());
        newInvoice.setStatus(InvoiceStatus.OUTSTANDING);
        newInvoice.setUsername(user.getUsername());
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
        invoiceRepo.save(newInvoice);
        
        return true;
    }



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
public Item ItemAddedToBasket(Long id) {
        Optional<Item> item = itemRepo.findById(id);
        if(item.isPresent()){
            itemRepo.save(item.get());
            return item.get();
        }
        return null;
    }
    
    @Override
public void ItemRemovedToBasket(Long id) {
    }
    
    @Override
public void addItem(Item item) {
        itemRepo.save(item);
    }

    public ShoppingServiceImpl() {
     
    }

    @Override
public List<Item> getAviliableItems() {
        return itemRepo.findAll();
    }

}
