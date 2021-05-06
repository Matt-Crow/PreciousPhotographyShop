package PreciousPhotographyShop.logging.encryption;

import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Properties;
import javax.crypto.Cipher;
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
    private static final int NUM_KEY_BYTES = 16;
    
    private final Cipher cipherAlgorithm;
    private final SecretKey key;
    private final IvParameterSpec iv;
    
    public Encrypter(Properties props) throws Exception {
        cipherAlgorithm = findSupportedCipher();
        key = generateKey(props.getProperty("key"));
        iv = new IvParameterSpec(Base64.getDecoder().decode(props.getProperty("iv")));
    }
    
    private Cipher findSupportedCipher() throws Exception {
        Cipher found = null;
        for(Provider p : Security.getProviders()){
            p.getServices().forEach((service)->{
                System.out.println(service.getAlgorithm());
            });
        }
        found = Cipher.getInstance("AES/CBC/PKCS5Padding");
        
        return found;
    }
    
    public final String encrypt(String plainText) throws Exception {
        cipherAlgorithm.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipherAlgorithm.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }
    
    public final String decrypt(String cipherText) throws Exception {
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
    
    private static byte[] generateInitVector(){
        byte[] vector = new byte[NUM_KEY_BYTES];
        new SecureRandom().nextBytes(vector);
        return vector;
    }
    
    public static void main(String[] args) throws Exception {
        EncryptionPropertyLoader loader = new EncryptionPropertyLoader();
        Properties props = null;
        if(loader.exists()){
            props = loader.load();
        } else {
            props = new Properties();
            props.setProperty("key", "password");
            props.setProperty("iv", Base64.getEncoder().encodeToString(generateInitVector()));
            loader.save(props);
        }
        
        Encrypter encrypter = new Encrypter(props);
        
        String orig = "Hello world!";
        String enc = encrypter.encrypt(orig);
        String dec = encrypter.decrypt(enc);
        System.out.printf("%s =>\n%s =>\n%s\n", orig, enc, dec);
    }
}
