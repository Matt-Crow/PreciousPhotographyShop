package PreciousPhotographyShop.logging.encryption;

import java.util.Base64;
import java.util.Properties;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Used to store encryption properties in the user's local file system
 * @author Matt
 */
public class EncryptionProperties extends Properties {
    private final String keyType;
    
    protected EncryptionProperties(String keyType){
        super();
        this.keyType = keyType;
    }
    
    protected final void setKey(SecretKey key){
        String keyAsString = Base64.getEncoder().encodeToString(key.getEncoded());
        this.setProperty("key", keyAsString);
    }
    
    protected final void setIv(IvParameterSpec iv){
        String ivAsStr = Base64.getEncoder().encodeToString(iv.getIV());
        this.setProperty("iv", ivAsStr);
    }
    
    protected final SecretKey getKey(){
        byte[] keyAsBytes = Base64.getDecoder().decode(getProperty("key"));
        return new SecretKeySpec(keyAsBytes, keyType);
    }
    
    protected final IvParameterSpec getIv(){
        byte[] ivAsBytes = Base64.getDecoder().decode(getProperty("iv"));
        return new IvParameterSpec(ivAsBytes);
    }
}
