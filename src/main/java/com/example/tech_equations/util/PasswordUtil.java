package com.example.tech_equations.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Hash a password
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Verify if the password matches the stored hash
    public static boolean verifyPassword(String password, String storedHash) {
        return BCrypt.checkpw(password, storedHash);
    }
}
