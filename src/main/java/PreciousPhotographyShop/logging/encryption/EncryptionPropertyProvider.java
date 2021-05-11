package PreciousPhotographyShop.logging.encryption;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * Provides values for new encryption properties.
 * 
 * @author Matt Crow
 */
public class EncryptionPropertyProvider {
    private final String encryptionType;
    private final int bytesInKey;
    
    public EncryptionPropertyProvider(String encryptionType, int numKeyBytes){
        this.encryptionType = encryptionType;
        this.bytesInKey = numKeyBytes;
    }
    
    public final SecretKey newSecretKey() throws NoSuchAlgorithmException{
        KeyGenerator gen = KeyGenerator.getInstance("AES");
        gen.init(bytesInKey * 8); // measured in bits, not bytes
        return gen.generateKey();
    }
    
    public final IvParameterSpec newIvParameter(){
        byte[] bitMask = new byte[bytesInKey];
        new SecureRandom().nextBytes(bitMask);
        return new IvParameterSpec(bitMask);
    }
    
    public final EncryptionProperties newEncryptionProperties() throws NoSuchAlgorithmException{
        EncryptionProperties props = new EncryptionProperties("AES");
        props.setKey(newSecretKey());
        props.setIv(newIvParameter());
        return props;
    }
}