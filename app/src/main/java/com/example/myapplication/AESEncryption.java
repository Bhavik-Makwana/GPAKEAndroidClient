package com.example.myapplication;



import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;

public class AESEncryption {

    byte[] keyBytes;
    Cipher cipher;
    SecretKeySpec key;

    public AESEncryption() {

        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING", "BC");
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createKey(byte[] keyBytes) {
        this.keyBytes = keyBytes;
        key = new SecretKeySpec(keyBytes, "AES");
    }

    public String encrypt(String plainText) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public String decrypt(String cipherText) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(cipherText)));
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
