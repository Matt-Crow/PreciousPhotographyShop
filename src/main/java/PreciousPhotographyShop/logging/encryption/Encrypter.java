package PreciousPhotographyShop.logging.encryption;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * https://www.baeldung.com/java-aes-encryption-decryption
 * 
 * @author Matt
 */
public class Encrypter {
    private final int NUM_KEY_BYTES = 16;
    
    public final String encrypt(String plainText, String password, byte[] vector) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException{
        Cipher cipherAlgorithm = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey key = generateKey(password);
        IvParameterSpec iv = new IvParameterSpec(vector);
        cipherAlgorithm.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipherAlgorithm.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }
    
    public final String decrypt(String cipherText, String password, byte[] vector) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException{
        Cipher cipherAlgorithm = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey key = generateKey(password);
        IvParameterSpec iv = new IvParameterSpec(vector);
        cipherAlgorithm.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipherAlgorithm.doFinal(Base64.getDecoder().decode(cipherText.getBytes()));
        return new String(plainText);
    }
    
    private SecretKey generateKey(String password) throws NoSuchAlgorithmException, InvalidKeySpecException{
        // not sure what key generator to use
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), "salt".getBytes(), 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        return secret;
    }
    
    private byte[] generateInitVector(){
        byte[] vector = new byte[NUM_KEY_BYTES];
        new SecureRandom().nextBytes(vector);
        return vector;
    }
    
    public static void main(String[] args) throws Exception {
        Encrypter encrypter = new Encrypter();
        String password = "password";
        byte[] secretVector = encrypter.generateInitVector();
        
        String orig = "Hello world!";
        String enc = encrypter.encrypt(orig, password, secretVector);
        String dec = encrypter.decrypt(enc, password, secretVector);
        System.out.printf("%s =>\n%s =>\n%s\n", orig, enc, dec);
        
        System.out.println(new String(encrypter.generateKey(password).getEncoded()));
    }
}
