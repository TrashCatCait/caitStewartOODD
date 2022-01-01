/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.solent.cait.oodd.service;

import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.solent.cait.oodd.dao.UserRepository;
import com.solent.cait.oodd.dao.ItemCatalogRepository;
import com.solent.cait.oodd.dao.InvoiceRepository;
import com.solent.cait.oodd.dto.User;
import com.solent.cait.oodd.dto.Item;
import com.solent.cait.oodd.dto.Roles;

/**
 *
 * @author caitlyn
 */
@Component
public class PopulateDatabaseOnStart {


    private static final String DEFAULT_ADMIN_USERNAME = "globaladmin";
    private static final String DEFAULT_ADMIN_PASSWORD = "globaladmin";

    private static final String DEFAULT_USER_PASSWORD = "user1234";
    private static final String DEFAULT_USER_USERNAME = "user1234";

    @Autowired
    private InvoiceRepository invoiceRepo;
    
    @Autowired
    private ItemCatalogRepository itemRepo;

    @Autowired
    private UserRepository userRepository;
    
    @PostConstruct
    public void initDatabase() {

        // initialising admin and normal user if dont exist
        User adminUser = new User();
        adminUser.setUsername(DEFAULT_ADMIN_USERNAME);
        adminUser.setFirstName("default administrator");
        adminUser.setPassword(DEFAULT_ADMIN_PASSWORD);
        adminUser.setUserRole(Roles.ADMIN);

        List<User> users = userRepository.findByUsername(DEFAULT_ADMIN_USERNAME);
        if (users.isEmpty()) {
            userRepository.save(adminUser);
        } else {
        }

        User defaultUser = new User();
        defaultUser.setUsername(DEFAULT_USER_USERNAME);
        defaultUser.setFirstName("default user");
        defaultUser.setPassword(DEFAULT_USER_PASSWORD);
        defaultUser.setUserRole(Roles.CUSTOMER);
        defaultUser.setCardExpire("03/25");
        defaultUser.setCardNumber("4259843967067284");
        defaultUser.setNameOnCard("Default User");
        users = userRepository.findByUsername(DEFAULT_USER_USERNAME);
        if (users.isEmpty()) {
            userRepository.save(defaultUser);
        } else {
        }

        Item defaultItem = new Item();
        defaultItem.setName("Hen");
        defaultItem.setPrice(400.00);
        defaultItem.setQuantity(19);
        defaultItem.setUuid(UUID.randomUUID().toString());
        defaultItem.setType("Animal");
        itemRepo.save(defaultItem);
    }
}
