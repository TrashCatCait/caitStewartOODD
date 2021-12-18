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
import com.solent.cait.oodd.dto.User;
import com.solent.cait.oodd.model.UserBasket;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
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

    @Override
    public void purchaseItems(UserBasket basket, User user) {
        Invoice newInvoice = new Invoice();
        newInvoice.setPurchasedItems(basket.getCurrentBasketItems());
        newInvoice.setAmountDue(basket.getTotal());
        newInvoice.setPurchaser(user);
        
        newInvoice.setDateOfPurchase(new Date());
        
        invoiceRepo.save(newInvoice);
    }
    
    @Override
    public void removeItemById(Long Id) {
        itemRepo.deleteById(Id); 
    }
    
    @Override
    public Boolean ItemExistsId(Long id) {
        return itemRepo.existsById(id);
    }

    @Override
    public Item ItemAddedToBasket(Long id) {
        Optional<Item> item = itemRepo.findById(id);
        if(item.isPresent()){
            item.get().setQuantity(item.get().getQuantity() - 1);
            itemRepo.save(item.get());
            return item.get();
        }
        return null;
    }
    
    @Override
    public void ItemRemovedToBasket(Long id) {
        Optional<Item> item = itemRepo.findById(id);
        if(item.isPresent()){
            item.get().setQuantity(item.get().getQuantity() + 1);
            itemRepo.save(item.get());
        }
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

