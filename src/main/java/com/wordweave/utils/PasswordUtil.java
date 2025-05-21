package com.wordweave.utils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utility class for secure password encryption and decryption using AES-GCM.
 * Provides methods for generating cryptographic keys, encrypting passwords,
 * and decrypting them using username as part of the key derivation.
 */
public class PasswordUtil {
	private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";

    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 12;
    private static final int SALT_LENGTH_BYTE = 16;
    private static final Charset UTF_8 = StandardCharsets.UTF_8;


    /**
     * Generates a secure random nonce (number used once) of specified length.
     * 
     * @param numBytes The length of the nonce in bytes
     * @return A byte array containing random bytes
     */
    public static byte[] getRandomNonce(int numBytes) {
        byte[] nonce = new byte[numBytes];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    /**
     * Generates a new AES secret key of specified size.
     * 
     * @param keysize The key size in bits (128, 192, or 256)
     * @return A new SecretKey for AES encryption
     * @throws NoSuchAlgorithmException If AES algorithm is not available
     */
    public static SecretKey getAESKey(int keysize) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keysize, SecureRandom.getInstanceStrong());
        return keyGen.generateKey();
    }

    /**
     * Derives an AES key from a password using PBKDF2 with HMAC-SHA256.
     * 
     * @param password The password to derive the key from
     * @param salt The random salt for key derivation
     * @return Derived SecretKey or null if derivation fails
     */
    public static SecretKey getAESKeyFromPassword(char[] password, byte[] salt){
           	try {
           		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
           		KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
           		SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
           		return secret;
       		} catch (NoSuchAlgorithmException ex) {
       			Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
           	} catch (InvalidKeySpecException ex) {
           		Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
           	}
       		return null;
    }

    /**
     * Encrypts a password using AES-GCM with username-derived key.
     * 
     * @param username Used as part of key derivation (treated as password in PBKDF2)
     * @param password The plaintext password to encrypt
     * @return Base64-encoded string containing IV + salt + ciphertext, or null if encryption fails
     */
    public static String encrypt(String username, String password){
    	try {
		    byte[] salt = getRandomNonce(SALT_LENGTH_BYTE);
		    byte[] iv = getRandomNonce(IV_LENGTH_BYTE);
		    SecretKey aesKeyFromPassword = getAESKeyFromPassword(username.toCharArray(), salt);

		    Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
		    cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

		    byte[] cipherText = cipher.doFinal(password.getBytes());
		    byte[] cipherTextWithIvSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length)
		            .put(iv)
		            .put(salt)
		            .put(cipherText)
		            .array();

		    return Base64.getEncoder().encodeToString(cipherTextWithIvSalt);
    	}catch(Exception ex) {
    		ex.printStackTrace();
    		return null;
    	}

    }


    /**
     * Decrypts a password encrypted by the encrypt() method.
     * 
     * @param encryptedPassword The Base64-encoded encrypted password (IV + salt + ciphertext)
     * @param username The username used during encryption for key derivation
     * @return The decrypted plaintext password, or null if decryption fails
     */
    public static String decrypt(String encryptedPassword, String username) {
		try {
			byte[] decode = Base64.getDecoder().decode(encryptedPassword.getBytes(UTF_8));
			ByteBuffer bb = ByteBuffer.wrap(decode);

			byte[] iv = new byte[IV_LENGTH_BYTE];
			bb.get(iv);

			byte[] salt = new byte[SALT_LENGTH_BYTE];
			bb.get(salt);

			byte[] cipherText = new byte[bb.remaining()];
			bb.get(cipherText);

			SecretKey aesKeyFromPassword = PasswordUtil.getAESKeyFromPassword(username.toCharArray(), salt);

			Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

			cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

			byte[] plainText = cipher.doFinal(cipherText);

			return new String(plainText, UTF_8);
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}
}
