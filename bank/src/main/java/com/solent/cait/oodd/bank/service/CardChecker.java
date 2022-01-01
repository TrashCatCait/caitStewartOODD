/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.solent.cait.oodd.bank.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author caitlyn
 */
public class CardChecker {
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
}
