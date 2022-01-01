/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solent.cait.oodd.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.solent.cait.oodd.dto.User;
/**
 *
 * @author cgallen
 * Repository for holding user accounts finding accounts and account creation.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 
     * @param username
     * @return 
     */
    @Query("select u from User u where u.username = :username")
    public List<User> findByUsername(@Param("username")String username);

    /**
     * 
     * @param firstName
     * @param secondName
     * @return 
     */
    @Query("select u from User u where u.firstName = :firstName and u.secondName = :secondName")
    public List<User> findByNames(@Param("firstName") String firstName, @Param("secondName") String secondName);

}
