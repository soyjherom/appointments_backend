package com.jherom.ventures.appointments_backend.utils;

import com.jherom.ventures.appointments_backend.exceptions.DecryptionException;
import com.jherom.ventures.appointments_backend.exceptions.EncryptionException;
import com.jherom.ventures.appointments_backend.exceptions.HashingException;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
public class CryptoUtil {
    private static final String ENCRYPTION_KEY = "my_temp_encrypti";

    public static String getHash(String value) throws HashingException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(value.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException nsaException) {
            final String message = "SHA Algorithm not found";
            log.error(message);
            throw new HashingException();
        }
    }

    public static String encrypt(String value) throws EncryptionException {
        try {
            SecretKeySpec key = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            final String message = "Error encrypting the value: {}";
            log.error(message, e.getMessage());
            throw new EncryptionException();
        }
    }

    public static String decrypt(String encryptedValue) throws DecryptionException {
        try {
            SecretKeySpec key = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedValue);
            byte[] decrypted = cipher.doFinal(decodedBytes);
            return new String(decrypted);
        } catch (Exception e) {
            final String message = "Error decrypting the value";
            log.error(message);
            throw new DecryptionException();
        }
    }
}
