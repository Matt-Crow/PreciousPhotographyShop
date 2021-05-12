package PreciousPhotographyShop.logging.encryption;

import java.security.Provider;
import java.security.Security;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * https://www.baeldung.com/java-aes-encryption-decryption
 * 
 * @author Matt
 */
public class Encrypter {    
    private final Cipher cipherAlgorithm;
    private final SecretKey key;
    private final IvParameterSpec iv;
    
    public Encrypter(EncryptionKeys keys) throws Exception {
        cipherAlgorithm = findSupportedCipher();
        key = keys.getKey();
        iv = keys.getIv();
    }
    
    private Cipher findSupportedCipher() throws Exception {
        Cipher found = null;
        for(Provider p : Security.getProviders()){
            p.getServices().forEach((service)->{
                //System.out.println(service.getAlgorithm());
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
    
    public static void main(String[] args) throws Exception {
        Encrypter encrypter = new EncryptionProvider().createDefaultEncrypter();
        
        String orig = "Hello world!";
        String enc = encrypter.encrypt(orig);
        String dec = encrypter.decrypt(enc);
        System.out.printf("%s =>\n%s =>\n%s\n", orig, enc, dec);
    }
}
