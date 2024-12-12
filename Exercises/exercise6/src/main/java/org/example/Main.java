package org.example;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class Main {
    public static void main(String[] args) throws Exception {
        // Symmetric Encryption
        System.out.println("Symmetric Encryption/Decryption:");

        // Key generation for AES-256
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // AES-256
        SecretKey aesKey = keyGen.generateKey();

        Cipher aesCipher = Cipher.getInstance("AES/GCM/NoPadding");
        byte[] iv = new byte[12];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv); // Generate random IV
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);

        // Encrypt
        aesCipher.init(Cipher.ENCRYPT_MODE, aesKey, gcmSpec);
        String plainText = "Hello Bob! This is Alice.";
        byte[] encryptedText = aesCipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        System.out.println("Encrypted: " + Base64.getEncoder().encodeToString(encryptedText));

        // Decrypt
        aesCipher.init(Cipher.DECRYPT_MODE, aesKey, gcmSpec);
        byte[] decryptedText = aesCipher.doFinal(encryptedText);
        System.out.println("Decrypted: " + new String(decryptedText, StandardCharsets.UTF_8));

        // Asymmetric Encryption
        System.out.println("\nAsymmetric Encryption/Decryption:");

        // Key pair generation for RSA
        KeyPairGenerator rsaKeyGen = KeyPairGenerator.getInstance("RSA");
        rsaKeyGen.initialize(2048);
        KeyPair aliceKeyPair = rsaKeyGen.generateKeyPair();
        KeyPair bobKeyPair = rsaKeyGen.generateKeyPair();

        // Encrypt with Bob's public key
        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsaCipher.init(Cipher.ENCRYPT_MODE, bobKeyPair.getPublic());
        encryptedText = rsaCipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        System.out.println("Encrypted: " + Base64.getEncoder().encodeToString(encryptedText));

        // Decrypt with Bob's private key
        rsaCipher.init(Cipher.DECRYPT_MODE, bobKeyPair.getPrivate());
        decryptedText = rsaCipher.doFinal(encryptedText);
        System.out.println("Decrypted: " + new String(decryptedText, StandardCharsets.UTF_8));

        // Signing and Validating Signature using RSA-2048
        System.out.println("\nSigning and Validating Signature:");

        // Sign the message with Alice's private key
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(aliceKeyPair.getPrivate());
        signature.update(plainText.getBytes(StandardCharsets.UTF_8));
        byte[] digitalSignature = signature.sign();
        System.out.println("Digital Signature: " + Base64.getEncoder().encodeToString(digitalSignature));

        // Validate the signature with Alice's public key
        signature.initVerify(aliceKeyPair.getPublic());
        signature.update(plainText.getBytes(StandardCharsets.UTF_8));
        boolean isSignatureValid = signature.verify(digitalSignature);
        System.out.println("Is signature valid? " + isSignatureValid);
    }
}