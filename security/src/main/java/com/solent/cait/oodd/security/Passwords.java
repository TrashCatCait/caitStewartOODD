package com.solent.cait.oodd.security;

import org.mindrot.jbcrypt.BCrypt;

/**
 * 
 * @author caitlyn
 */
public class Passwords {
    /**
     * @method hashPassword
     * @param password
     * @return hashedString
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
