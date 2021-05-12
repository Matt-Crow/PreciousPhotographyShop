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
public class EncryptionKeyProvider {
    private final String encryptionType;
    private final int bytesInKey;
    
    public EncryptionKeyProvider(String encryptionType, int numKeyBytes){
        this.encryptionType = encryptionType;
        this.bytesInKey = numKeyBytes;
    }
    
    public final EncryptionKeys newKeys() throws NoSuchAlgorithmException{
        EncryptionKeys keys = new EncryptionKeys(
            encryptionType,
            bytesInKey,
            newSecretKey(),
            newIvParameter()
        );
        return keys;
    }
    
    private SecretKey newSecretKey() throws NoSuchAlgorithmException{
        KeyGenerator gen = KeyGenerator.getInstance(encryptionType);
        gen.init(bytesInKey * 8); // measured in bits, not bytes
        return gen.generateKey();
    }
    
    private IvParameterSpec newIvParameter(){
        byte[] bitMask = new byte[bytesInKey];
        new SecureRandom().nextBytes(bitMask);
        return new IvParameterSpec(bitMask);
    }
    
    
}
