/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.solent.cait.oodd.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Embedded;
import com.solent.cait.oodd.security.Passwords;

/**
 *
 * @author caitlyn
 * User is stored in the User repo and stores user details allowing them to log in
 * password is transient so is not actually stored in the database only hashed password
 * is stored for security reasons. 
 */
@Entity
public class User {

    private Long id;

    private String firstName = "";

    private String secondName = "";

    private String username = "";

    private String password = "";

    private String hashedPassword = "";

    private Address address;

    private Roles userRole;

    private Boolean enabled = true;
    
    private String cardNumber;
    
    private String cardExpire;
    
    private String nameOnCard;
    
    /**
     * 
     * @return 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return 
     */
    public String getCardNumber() {
        return cardNumber;
    }
    
    /**
     * 
     * @param cardNumber 
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    /**
     * 
     * @return 
     */
    public String getNameOnCard() {
        return nameOnCard;
    }
    
    /**
     * 
     * @param nameOnCard 
     */
    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }
    
    /**
     * 
     * @return 
     */
    public String getCardExpire() {
        return cardExpire;
    }
    
    /**
     * 
     * @param cardExpire 
     */
    public void setCardExpire(String cardExpire) {
        this.cardExpire = cardExpire;
    }
    
    /**
     * 
     * @return 
     */
    public Roles getUserRole() {
        return userRole;
    }

    /**
     * 
     * @param userRole 
     */
    public void setUserRole(Roles userRole) {
        this.userRole = userRole;
    }

    /**
     * 
     * @return 
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * 
     * @param username 
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 
     * @return 
     */
    @Embedded
    public Address getAddress() {
        return address;
    }

    /**
     * 
     * @param address 
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * 
     * @return 
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * @param firstName 
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * 
     * @return 
     */
    public String getSecondName() {
        return secondName;
    }

    /**
     * 
     * @param secondName 
     */
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    /**
     * 
     * @return 
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * 
     * @param enabled 
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 
     * @return 
     */
    // passwords not saved in database only passwordhash is saved
    @Transient
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @param password 
     */
    public void setPassword(String password) {
        this.password = password;
        setHashedPassword(Passwords.hashPassword(password));
    }

    /**
     * 
     * @param checkPassword
     * @return 
     */
    public boolean isValidPassword(String checkPassword) {
        if (checkPassword == null || getHashedPassword() == null) {
            return false;
        }
        return Passwords.checkPassword(checkPassword, getHashedPassword());
    }

    /**
     * 
     * @return 
     */
    public String getHashedPassword() {
        return hashedPassword;
    }
    
    /**
     * 
     * @param hashedPassword 
     */
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", firstName=" + firstName + ", secondName=" + secondName + ", username=" + username + ", password=NOT SHOWN, address=" + address + ", userRole=" + userRole + '}';
    }

}
