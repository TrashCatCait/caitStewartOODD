package com.solent.cait.oodd.security;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author caitlyn
 */
public class Passwords {
    public static String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
    
    public static boolean checkPassword(String password, String hashed){
        return BCrypt.checkpw(password, hashed);
    }
    
}
