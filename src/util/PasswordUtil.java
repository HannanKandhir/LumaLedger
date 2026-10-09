package util;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtil {

    private static final SecureRandom RANDOM =
            new SecureRandom();

    private static final int ITERATIONS =
            120000;

    private static final int KEY_LENGTH =
            256;

    public static String generateSalt() {

        byte[] salt =
                new byte[16];

        RANDOM.nextBytes(salt);

        return Base64
                .getEncoder()
                .encodeToString(
                        salt
                );
    }

    public static String hashPassword(
            String password,
            String saltBase64) {

        try {

            byte[] salt =
                    Base64
                            .getDecoder()
                            .decode(
                                    saltBase64
                            );

            PBEKeySpec spec =
                    new PBEKeySpec(
                            password.toCharArray(),
                            salt,
                            ITERATIONS,
                            KEY_LENGTH
                    );

            SecretKeyFactory factory =
                    SecretKeyFactory
                            .getInstance(
                                    "PBKDF2WithHmacSHA256"
                            );

            byte[] hash =
                    factory
                            .generateSecret(
                                    spec
                            )
                            .getEncoded();

            spec.clearPassword();

            return Base64
                    .getEncoder()
                    .encodeToString(
                            hash
                    );

        } catch (Exception e) {

            throw new IllegalStateException(
                    "Could not hash password",
                    e
            );
        }
    }

    public static boolean verifyPassword(
            String password,
            String salt,
            String expectedHash) {

        return hashPassword(
                password,
                salt
        ).equals(
                expectedHash
        );
    }
}