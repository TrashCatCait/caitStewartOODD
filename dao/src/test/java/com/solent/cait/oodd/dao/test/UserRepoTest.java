/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.solent.cait.oodd.dao.test;

/**
 *
 * @author caitlyn
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import java.util.Optional;
import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import com.solent.cait.oodd.dao.UserRepository;
import com.solent.cait.oodd.dao.UserRepository;
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


/**
 *
 * @author caitlyn
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DAOTestConfiguration.class, loader = AnnotationConfigContextLoader.class)
@Transactional
public class UserRepoTest {

    private static final Logger LOG = LogManager.getLogger(UserRepoTest.class);

    @Autowired
    private UserRepository userRepo;

    @Test
    public void testUserRepo() {
        LOG.debug("Starting item repo Test");
        
        //Clean out the repo
        userRepo.deleteAll();
        
        User userToDel = new User();
        userToDel.setFirstName("Del");
        userToDel.setUsername("user12345");
        
        User userToSave = new User();
        userToDel.setFirstName("newtester");
        userToSave.setSecondName("Secondname");
        userToDel.setUsername("newtestuser");
        
        userToDel = userRepo.save(userToDel);
        userToSave = userRepo.save(userToSave);
        
        //Check two items are present
        assertEquals(2, userRepo.count());
        
        //Delete Item
        userRepo.deleteById(userToDel.getId());
                
        //Check only one exist now
        assertEquals(1, userRepo.count());
        
        List<User> user = userRepo.findByNames("newtester", "Secondname");
        List<User> username = userRepo.findByUsername("newtestuser");
        Optional<User> userid = userRepo.findById(userToSave.getId());
        
        LOG.debug("Found User By Id: " + userid.get());
        LOG.debug("Found Item By Name " + user);
        LOG.debug("Found by Username: " + username);
        LOG.debug("Test complete");
    }
}
