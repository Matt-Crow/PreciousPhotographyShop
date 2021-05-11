package PreciousPhotographyShop.logging.encryption;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

/**
 *
 * @author Matt
 */
@Component
@SessionScope
public class EncryptionKeys {
    private final String keyType;
    private final int size;
    private final SecretKey key;
    private final IvParameterSpec iv;
    
    public EncryptionKeys(String keyType, int size, SecretKey key, IvParameterSpec iv){
        this.keyType = keyType;
        this.size = size;
        this.key = key;
        this.iv = iv;
    }
    
    public final String getKeyType(){
        return keyType;
    }
    
    public final int getSize(){
        return size;
    }
    
    public final SecretKey getKey(){
        return key;
    }
    
    public final IvParameterSpec getIv(){
        return iv;
    }
}
