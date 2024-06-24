import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class AuthUtil {

    /**
     * Hashes a password using PBKDF2
     * @param password Password to hash
     * @return An array of two strings. the first one is the hashed password and the second one is the salt
     */
    public static String[] hashPassword(String password) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            byte[] hashedPassword = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(spec).getEncoded();

            String saltString = Base64.getEncoder().encodeToString(salt);
            String hashedPasswordString = Base64.getEncoder().encodeToString(hashedPassword);
            return new String[]{hashedPasswordString, saltString};
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if a password matches a hashed password (hashed with PBKDF2)
     * @param password The password to check
     * @param hashString The hash to check against
     * @param saltString The salt used for hashing
     * @return {@code true} if The password matches with the hash
     */
    public static boolean checkPassword(String password, String hashString, String saltString) {
        byte[] salt = Base64.getDecoder().decode(saltString);

        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            byte[] hashedPassword = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(spec).getEncoded();
            String hashedPasswordString = Base64.getEncoder().encodeToString(hashedPassword);

            return hashedPasswordString.equals(hashString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
