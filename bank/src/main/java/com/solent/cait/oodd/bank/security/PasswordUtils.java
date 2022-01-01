/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solent.cait.oodd.bank.security;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author caitlyn
 * Password utilities for the bank client
 */
public class PasswordUtils {
    
    /**
     * 
     * @param password
     * @return 
     */
    public static String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
    
    /**
     * 
     * @param password
     * @param hash
     * @return 
     */
    public static boolean checkPassword(String password, String hash){
        return BCrypt.checkpw(password, hash);
    }
    
}
