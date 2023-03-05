package net.therap.onlinestore.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 1/11/23
 */
public class Encryption {

    private static final int ITERATION_COUNT = 5000;
    private static final int KEY_LENGTH = 128;
    private static final String ENCRYPTION_ALGO = "PBKDF2WithHmacSHA1";

    public static String getPBKDF2(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {

        if (Objects.isNull(password) || password.isEmpty()) {
            return password;
        }

        byte[] salt = new byte[]{2, 3, 5, 7, 11, 13, 17, 23, 29};
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ENCRYPTION_ALGO);

        return Base64.getEncoder().encodeToString(factory.generateSecret(spec).getEncoded());
    }
}
