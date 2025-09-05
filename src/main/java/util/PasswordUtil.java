package util;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordUtil {
    private PasswordUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * Hashes the given password using SHA-256
     * @param password the password to hash
     * @return the hashed password
     */
    public static String hashPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return DigestUtils.sha256Hex(password);
    }

    /**
     * Verifies a password against a hashed password
     * @param inputPassword the password to verify
     * @param hashedPassword the hashed password to compare against
     * @return true if the password matches the hash, false otherwise
     */
    public static boolean verifyPassword(String inputPassword, String hashedPassword) {
        if (inputPassword == null || hashedPassword == null) {
            return false;
        }
        String hashedInput = hashPassword(inputPassword);
        return hashedInput.equals(hashedPassword);
    }
}
