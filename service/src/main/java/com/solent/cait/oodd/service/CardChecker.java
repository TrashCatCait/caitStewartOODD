/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.solent.cait.oodd.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author caitlyn
 * 
 * Basic class to take in card details and check them against some predefined 
 * Constants about cards to check if it's a valid card at the moment it can only
 * validate VISA cards using Visa's regex string and just checks the date and the
 * CVV are the expect length.
 */
public class CardChecker {
    /**
     * 
     * @param str
     * @return 
     */
    public boolean isValidCard(String str) {
        // Regex to check valid.
        // Visa Card number
        String regex = "^4[0-9]{12}(?:[0-9]{3})?$";
        
        Pattern pattern = Pattern.compile(regex);
        
        if(str == null) {
            return false;
        }
        
        Matcher match = pattern.matcher(str);
        return match.matches();
    }
    
    /**
     * 
     * @param str
     * @return 
     */
    public boolean checkCVV(String str) {
        if(str.length() == 3) {
            return true;
        }
        return false;
    }
    
    /**
     * 
     * @param str
     * @return 
     */
    public boolean checkExpire(String str) {
        if(str.length() == 5) {
            return true;
        }
        return false;
    }
}
